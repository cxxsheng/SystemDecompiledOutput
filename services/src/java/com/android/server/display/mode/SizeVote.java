package com.android.server.display.mode;

/* loaded from: classes.dex */
class SizeVote implements com.android.server.display.mode.Vote {
    final int mHeight;
    final int mMinHeight;
    final int mMinWidth;
    final int mWidth;

    SizeVote(int i, int i2, int i3, int i4) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mMinWidth = i3;
        this.mMinHeight = i4;
    }

    @Override // com.android.server.display.mode.Vote
    public void updateSummary(com.android.server.display.mode.VoteSummary voteSummary) {
        if (this.mHeight > 0 && this.mWidth > 0) {
            if (voteSummary.width == -1 && voteSummary.height == -1) {
                voteSummary.width = this.mWidth;
                voteSummary.height = this.mHeight;
                voteSummary.minWidth = this.mMinWidth;
                voteSummary.minHeight = this.mMinHeight;
                return;
            }
            if (voteSummary.mIsDisplayResolutionRangeVotingEnabled) {
                voteSummary.width = java.lang.Math.min(voteSummary.width, this.mWidth);
                voteSummary.height = java.lang.Math.min(voteSummary.height, this.mHeight);
                voteSummary.minWidth = java.lang.Math.max(voteSummary.minWidth, this.mMinWidth);
                voteSummary.minHeight = java.lang.Math.max(voteSummary.minHeight, this.mMinHeight);
            }
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.display.mode.SizeVote)) {
            return false;
        }
        com.android.server.display.mode.SizeVote sizeVote = (com.android.server.display.mode.SizeVote) obj;
        return this.mWidth == sizeVote.mWidth && this.mHeight == sizeVote.mHeight && this.mMinWidth == sizeVote.mMinWidth && this.mMinHeight == sizeVote.mMinHeight;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mWidth), java.lang.Integer.valueOf(this.mHeight), java.lang.Integer.valueOf(this.mMinWidth), java.lang.Integer.valueOf(this.mMinHeight));
    }

    public java.lang.String toString() {
        return "SizeVote{ mWidth=" + this.mWidth + ", mHeight=" + this.mHeight + ", mMinWidth=" + this.mMinWidth + ", mMinHeight=" + this.mMinHeight + " }";
    }
}
