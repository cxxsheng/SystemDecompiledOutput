package com.android.server.am;

/* loaded from: classes.dex */
final class ProcessCachedOptimizerRecord {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String IS_FROZEN = "isFrozen";
    private final com.android.server.am.ProcessRecord mApp;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private long mEarliestFreezableTimeMillis;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mForceCompact;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mFreezeExempt;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mFreezeSticky;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private long mFreezeUnfreezeTime;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean mFreezerOverride;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mFrozen;
    private boolean mHasCollectedFrozenPSS;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private com.android.server.am.CachedAppOptimizer.CompactProfile mLastCompactProfile;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private long mLastCompactTime;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private int mLastOomAdjChangeReason;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private long mLastUsedTimeout;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mPendingCompact;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mPendingFreeze;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private com.android.server.am.CachedAppOptimizer.CompactProfile mReqCompactProfile;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private com.android.server.am.CachedAppOptimizer.CompactSource mReqCompactSource;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mShouldNotFreeze;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    long getLastCompactTime() {
        return this.mLastCompactTime;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setLastCompactTime(long j) {
        this.mLastCompactTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    com.android.server.am.CachedAppOptimizer.CompactProfile getReqCompactProfile() {
        return this.mReqCompactProfile;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setReqCompactProfile(com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile) {
        this.mReqCompactProfile = compactProfile;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    com.android.server.am.CachedAppOptimizer.CompactSource getReqCompactSource() {
        return this.mReqCompactSource;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setReqCompactSource(com.android.server.am.CachedAppOptimizer.CompactSource compactSource) {
        this.mReqCompactSource = compactSource;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setLastOomAdjChangeReason(int i) {
        this.mLastOomAdjChangeReason = i;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    int getLastOomAdjChangeReason() {
        return this.mLastOomAdjChangeReason;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    com.android.server.am.CachedAppOptimizer.CompactProfile getLastCompactProfile() {
        if (this.mLastCompactProfile == null) {
            this.mLastCompactProfile = com.android.server.am.CachedAppOptimizer.CompactProfile.SOME;
        }
        return this.mLastCompactProfile;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setLastCompactProfile(com.android.server.am.CachedAppOptimizer.CompactProfile compactProfile) {
        this.mLastCompactProfile = compactProfile;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean hasPendingCompact() {
        return this.mPendingCompact;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setHasPendingCompact(boolean z) {
        this.mPendingCompact = z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean isForceCompact() {
        return this.mForceCompact;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setForceCompact(boolean z) {
        this.mForceCompact = z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean isFrozen() {
        return this.mFrozen;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setFrozen(boolean z) {
        this.mFrozen = z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setFreezeSticky(boolean z) {
        this.mFreezeSticky = z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean isFreezeSticky() {
        return this.mFreezeSticky;
    }

    boolean skipPSSCollectionBecauseFrozen() {
        boolean z = this.mHasCollectedFrozenPSS;
        if (!this.mFrozen) {
            return false;
        }
        this.mHasCollectedFrozenPSS = true;
        return z;
    }

    void setHasCollectedFrozenPSS(boolean z) {
        this.mHasCollectedFrozenPSS = z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean hasFreezerOverride() {
        return this.mFreezerOverride;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setFreezerOverride(boolean z) {
        this.mFreezerOverride = z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    long getFreezeUnfreezeTime() {
        return this.mFreezeUnfreezeTime;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setFreezeUnfreezeTime(long j) {
        this.mFreezeUnfreezeTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean shouldNotFreeze() {
        return this.mShouldNotFreeze;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setShouldNotFreeze(boolean z) {
        setShouldNotFreeze(z, false);
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean setShouldNotFreeze(boolean z, boolean z2) {
        if (z2) {
            return this.mFrozen && !z;
        }
        this.mShouldNotFreeze = z;
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    long getEarliestFreezableTime() {
        return this.mEarliestFreezableTimeMillis;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setEarliestFreezableTime(long j) {
        this.mEarliestFreezableTimeMillis = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    long getLastUsedTimeout() {
        return this.mLastUsedTimeout;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setLastUsedTimeout(long j) {
        this.mLastUsedTimeout = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean isFreezeExempt() {
        return this.mFreezeExempt;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setPendingFreeze(boolean z) {
        this.mPendingFreeze = z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean isPendingFreeze() {
        return this.mPendingFreeze;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setFreezeExempt(boolean z) {
        this.mFreezeExempt = z;
    }

    ProcessCachedOptimizerRecord(com.android.server.am.ProcessRecord processRecord) {
        this.mApp = processRecord;
        this.mProcLock = processRecord.mService.mProcLock;
    }

    void init(long j) {
        this.mFreezeUnfreezeTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void dump(java.io.PrintWriter printWriter, java.lang.String str, long j) {
        printWriter.print(str);
        printWriter.print("lastCompactTime=");
        printWriter.print(this.mLastCompactTime);
        printWriter.print(" lastCompactProfile=");
        printWriter.println(this.mLastCompactProfile);
        printWriter.print(str);
        printWriter.print("hasPendingCompaction=");
        printWriter.print(this.mPendingCompact);
        printWriter.print(str);
        printWriter.print("isFreezeExempt=");
        printWriter.print(this.mFreezeExempt);
        printWriter.print(" isPendingFreeze=");
        printWriter.print(this.mPendingFreeze);
        printWriter.print(" isFrozen=");
        printWriter.println(this.mFrozen);
        printWriter.print(str);
        printWriter.print("earliestFreezableTimeMs=");
        android.util.TimeUtils.formatDuration(this.mEarliestFreezableTimeMillis, j, printWriter);
        printWriter.println();
    }
}
