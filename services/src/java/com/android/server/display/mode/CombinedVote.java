package com.android.server.display.mode;

/* loaded from: classes.dex */
class CombinedVote implements com.android.server.display.mode.Vote {
    final java.util.List<com.android.server.display.mode.Vote> mVotes;

    CombinedVote(java.util.List<com.android.server.display.mode.Vote> list) {
        this.mVotes = java.util.Collections.unmodifiableList(list);
    }

    @Override // com.android.server.display.mode.Vote
    public void updateSummary(final com.android.server.display.mode.VoteSummary voteSummary) {
        this.mVotes.forEach(new java.util.function.Consumer() { // from class: com.android.server.display.mode.CombinedVote$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.display.mode.Vote) obj).updateSummary(com.android.server.display.mode.VoteSummary.this);
            }
        });
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.display.mode.CombinedVote)) {
            return false;
        }
        return java.util.Objects.equals(this.mVotes, ((com.android.server.display.mode.CombinedVote) obj).mVotes);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mVotes);
    }

    public java.lang.String toString() {
        return "CombinedVote{ mVotes=" + this.mVotes + " }";
    }
}
