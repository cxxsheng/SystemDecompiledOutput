package com.android.server.display.mode;

/* loaded from: classes.dex */
class VotesStorage {

    @com.android.internal.annotations.VisibleForTesting
    static final int GLOBAL_ID = -1;
    private static final java.lang.String TAG = "VotesStorage";
    private final com.android.server.display.mode.VotesStorage.Listener mListener;
    private boolean mLoggingEnabled;
    private final java.lang.Object mStorageLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mStorageLock"})
    private final android.util.SparseArray<android.util.SparseArray<com.android.server.display.mode.Vote>> mVotesByDisplay = new android.util.SparseArray<>();

    @android.annotation.Nullable
    private final com.android.server.display.mode.VotesStatsReporter mVotesStatsReporter;

    interface Listener {
        void onChanged();
    }

    VotesStorage(@android.annotation.NonNull com.android.server.display.mode.VotesStorage.Listener listener, @android.annotation.Nullable com.android.server.display.mode.VotesStatsReporter votesStatsReporter) {
        this.mListener = listener;
        this.mVotesStatsReporter = votesStatsReporter;
    }

    void setLoggingEnabled(boolean z) {
        this.mLoggingEnabled = z;
    }

    @android.annotation.NonNull
    android.util.SparseArray<com.android.server.display.mode.Vote> getVotes(int i) {
        android.util.SparseArray<com.android.server.display.mode.Vote> clone;
        android.util.SparseArray<com.android.server.display.mode.Vote> clone2;
        synchronized (this.mStorageLock) {
            try {
                android.util.SparseArray<com.android.server.display.mode.Vote> sparseArray = this.mVotesByDisplay.get(i);
                clone = sparseArray != null ? sparseArray.clone() : new android.util.SparseArray<>();
                android.util.SparseArray<com.android.server.display.mode.Vote> sparseArray2 = this.mVotesByDisplay.get(-1);
                clone2 = sparseArray2 != null ? sparseArray2.clone() : new android.util.SparseArray<>();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (int i2 = 0; i2 < clone2.size(); i2++) {
            int keyAt = clone2.keyAt(i2);
            if (!clone.contains(keyAt)) {
                clone.put(keyAt, clone2.valueAt(i2));
            }
        }
        return clone;
    }

    void updateGlobalVote(int i, @android.annotation.Nullable com.android.server.display.mode.Vote vote) {
        updateVote(-1, i, vote);
    }

    void updateVote(int i, int i2, @android.annotation.Nullable com.android.server.display.mode.Vote vote) {
        android.util.SparseArray<com.android.server.display.mode.Vote> sparseArray;
        boolean z;
        if (this.mLoggingEnabled) {
            android.util.Slog.i(TAG, "updateVoteLocked(displayId=" + i + ", priority=" + com.android.server.display.mode.Vote.priorityToString(i2) + ", vote=" + vote + ")");
        }
        if (i2 < 0 || i2 > 17) {
            android.util.Slog.w(TAG, "Received a vote with an invalid priority, ignoring: priority=" + com.android.server.display.mode.Vote.priorityToString(i2) + ", vote=" + vote);
            return;
        }
        synchronized (this.mStorageLock) {
            try {
                if (this.mVotesByDisplay.contains(i)) {
                    sparseArray = this.mVotesByDisplay.get(i);
                } else {
                    sparseArray = new android.util.SparseArray<>();
                    this.mVotesByDisplay.put(i, sparseArray);
                }
                com.android.server.display.mode.Vote vote2 = sparseArray.get(i2);
                z = true;
                if (vote != null && !vote.equals(vote2)) {
                    sparseArray.put(i2, vote);
                } else if (vote == null && vote2 != null) {
                    sparseArray.remove(i2);
                } else {
                    z = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.i(TAG, "Updated votes for display=" + i + " votes=" + sparseArray);
        }
        if (z) {
            if (this.mVotesStatsReporter != null) {
                this.mVotesStatsReporter.reportVoteChanged(i, i2, vote);
            }
            this.mListener.onChanged();
        }
    }

    void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        int i;
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        synchronized (this.mStorageLock) {
            for (int i2 = 0; i2 < this.mVotesByDisplay.size(); i2++) {
                try {
                    sparseArray.put(this.mVotesByDisplay.keyAt(i2), this.mVotesByDisplay.valueAt(i2).clone());
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        printWriter.println("  mVotesByDisplay:");
        for (i = 0; i < sparseArray.size(); i++) {
            android.util.SparseArray sparseArray2 = (android.util.SparseArray) sparseArray.valueAt(i);
            if (sparseArray2.size() != 0) {
                printWriter.println("    " + sparseArray.keyAt(i) + ":");
                for (int i3 = 17; i3 >= 0; i3--) {
                    com.android.server.display.mode.Vote vote = (com.android.server.display.mode.Vote) sparseArray2.get(i3);
                    if (vote != null) {
                        printWriter.println("      " + com.android.server.display.mode.Vote.priorityToString(i3) + " -> " + vote);
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void injectVotesByDisplay(android.util.SparseArray<android.util.SparseArray<com.android.server.display.mode.Vote>> sparseArray) {
        synchronized (this.mStorageLock) {
            try {
                this.mVotesByDisplay.clear();
                for (int i = 0; i < sparseArray.size(); i++) {
                    this.mVotesByDisplay.put(sparseArray.keyAt(i), sparseArray.valueAt(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
