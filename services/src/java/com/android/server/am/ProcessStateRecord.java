package com.android.server.am;

/* loaded from: classes.dex */
final class ProcessStateRecord {
    private static final boolean TRACE_OOM_ADJ = false;
    private static final int VALUE_FALSE = 0;
    private static final int VALUE_INVALID = -1;
    private static final int VALUE_TRUE = 1;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mAdjSeq;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private java.lang.Object mAdjSource;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mAdjSourceProcState;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private java.lang.Object mAdjTarget;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private java.lang.String mAdjType;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mAdjTypeCode;
    private final com.android.server.am.ProcessRecord mApp;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private long mCacheOomRankerRss;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private long mCacheOomRankerRssTimeMs;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCacheOomRankerUseCount;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCompletedAdjSeq;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mContainsCycle;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private long mFgInteractionTime;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private java.lang.Object mForcingToImportant;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mHasForegroundActivities;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mHasOverlayUi;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mHasShownUi;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mHasStartedServices;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mHasTopUi;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private long mInteractionEventTime;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private long mLastCanKillOnBgRestrictedAndIdleTime;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private long mLastInvisibleTime;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private long mLastStateTime;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mNoKillOnBgRestrictedAndIdle;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mNotCachedSinceIdle;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mProcStateChanged;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mReachable;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mRepForegroundActivities;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mReportedInteraction;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mRunningRemoteAnimation;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mSavedPriority;
    private final com.android.server.am.ActivityManagerService mService;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mServiceB;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mServiceHighRam;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mSetCached;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mSetNoKillOnBgRestrictedAndIdle;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mSystemNoUi;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private long mWhenUnimportant;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mMaxAdj = 1001;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mCurRawAdj = com.android.server.am.ProcessList.INVALID_ADJ;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mSetRawAdj = com.android.server.am.ProcessList.INVALID_ADJ;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mCurAdj = com.android.server.am.ProcessList.INVALID_ADJ;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mSetAdj = com.android.server.am.ProcessList.INVALID_ADJ;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mVerifiedAdj = com.android.server.am.ProcessList.INVALID_ADJ;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mCurCapability = 0;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mSetCapability = 0;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mCurSchedGroup = 0;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mSetSchedGroup = 0;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mCurProcState = 20;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mRepProcState = 20;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mCurRawProcState = 20;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private int mSetProcState = 20;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private long mLastTopTime = Long.MIN_VALUE;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mBackgroundRestricted = false;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mCurBoundByNonBgRestrictedApp = false;
    private boolean mSetBoundByNonBgRestrictedApp = false;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCachedHasActivities = -1;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCachedIsHeavyWeight = -1;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCachedHasVisibleActivities = -1;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCachedIsHomeProcess = -1;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCachedIsPreviousProcess = -1;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCachedHasRecentTasks = -1;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCachedIsReceivingBroadcast = -1;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int[] mCachedCompatChanges = {-1, -1, -1};

    @com.android.internal.annotations.GuardedBy({"mService"})
    private java.lang.String mCachedAdjType = null;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCachedAdj = com.android.server.am.ProcessList.INVALID_ADJ;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mCachedForegroundActivities = false;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCachedProcState = 19;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mCachedSchedGroup = 0;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mScheduleLikeTopApp = false;

    ProcessStateRecord(com.android.server.am.ProcessRecord processRecord) {
        this.mApp = processRecord;
        this.mService = processRecord.mService;
        this.mProcLock = this.mService.mProcLock;
    }

    void init(long j) {
        this.mLastStateTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setMaxAdj(int i) {
        this.mMaxAdj = i;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getMaxAdj() {
        return this.mMaxAdj;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCurRawAdj(int i) {
        setCurRawAdj(i, false);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    boolean setCurRawAdj(int i, boolean z) {
        if (z) {
            return this.mCurRawAdj > i;
        }
        this.mCurRawAdj = i;
        this.mApp.getWindowProcessController().setPerceptible(i <= 200);
        return false;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getCurRawAdj() {
        return this.mCurRawAdj;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setSetRawAdj(int i) {
        this.mSetRawAdj = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSetRawAdj() {
        return this.mSetRawAdj;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCurAdj(int i) {
        this.mCurAdj = i;
        this.mApp.getWindowProcessController().setCurrentAdj(i);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getCurAdj() {
        return this.mCurAdj;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setSetAdj(int i) {
        this.mSetAdj = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSetAdj() {
        return this.mSetAdj;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSetAdjWithServices() {
        if (this.mSetAdj >= 900 && this.mHasStartedServices) {
            return com.android.server.am.ProcessList.SERVICE_B_ADJ;
        }
        return this.mSetAdj;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setVerifiedAdj(int i) {
        this.mVerifiedAdj = i;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getVerifiedAdj() {
        return this.mVerifiedAdj;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCurCapability(int i) {
        this.mCurCapability = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getCurCapability() {
        return this.mCurCapability;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setSetCapability(int i) {
        this.mSetCapability = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSetCapability() {
        return this.mSetCapability;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCurrentSchedulingGroup(int i) {
        this.mCurSchedGroup = i;
        this.mApp.getWindowProcessController().setCurrentSchedulingGroup(i);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getCurrentSchedulingGroup() {
        return this.mCurSchedGroup;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setSetSchedGroup(int i) {
        this.mSetSchedGroup = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSetSchedGroup() {
        return this.mSetSchedGroup;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCurProcState(int i) {
        this.mCurProcState = i;
        this.mApp.getWindowProcessController().setCurrentProcState(this.mCurProcState);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getCurProcState() {
        return this.mCurProcState;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCurRawProcState(int i) {
        setCurRawProcState(i, false);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    boolean setCurRawProcState(int i, boolean z) {
        if (z) {
            return this.mCurRawProcState > i;
        }
        this.mCurRawProcState = i;
        return false;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getCurRawProcState() {
        return this.mCurRawProcState;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setReportedProcState(int i) {
        this.mRepProcState = i;
        this.mApp.getWindowProcessController().setReportedProcState(i);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getReportedProcState() {
        return this.mRepProcState;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void forceProcessStateUpTo(int i) {
        if (this.mRepProcState > i) {
            com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    int i2 = this.mRepProcState;
                    setReportedProcState(i);
                    setCurProcState(i);
                    setCurRawProcState(i);
                    this.mService.mOomAdjuster.onProcessStateChanged(this.mApp, i2);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setSetProcState(int i) {
        if (android.app.ActivityManager.isProcStateCached(this.mSetProcState) && !android.app.ActivityManager.isProcStateCached(i)) {
            this.mCacheOomRankerUseCount++;
        }
        this.mSetProcState = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSetProcState() {
        return this.mSetProcState;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setLastStateTime(long j) {
        this.mLastStateTime = j;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    long getLastStateTime() {
        return this.mLastStateTime;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setSavedPriority(int i) {
        this.mSavedPriority = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSavedPriority() {
        return this.mSavedPriority;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setServiceB(boolean z) {
        this.mServiceB = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isServiceB() {
        return this.mServiceB;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setServiceHighRam(boolean z) {
        this.mServiceHighRam = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isServiceHighRam() {
        return this.mServiceHighRam;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setNotCachedSinceIdle(boolean z) {
        this.mNotCachedSinceIdle = z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean isNotCachedSinceIdle() {
        return this.mNotCachedSinceIdle;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setHasStartedServices(boolean z) {
        this.mHasStartedServices = z;
        if (z) {
            this.mApp.mProfile.addHostingComponentType(128);
        } else {
            this.mApp.mProfile.clearHostingComponentType(128);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean hasStartedServices() {
        return this.mHasStartedServices;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setHasForegroundActivities(boolean z) {
        this.mHasForegroundActivities = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean hasForegroundActivities() {
        return this.mHasForegroundActivities;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setRepForegroundActivities(boolean z) {
        this.mRepForegroundActivities = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean hasRepForegroundActivities() {
        return this.mRepForegroundActivities;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setHasShownUi(boolean z) {
        this.mHasShownUi = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean hasShownUi() {
        return this.mHasShownUi;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setHasTopUi(boolean z) {
        this.mHasTopUi = z;
        this.mApp.getWindowProcessController().setHasTopUi(z);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean hasTopUi() {
        return this.mHasTopUi;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setHasOverlayUi(boolean z) {
        this.mHasOverlayUi = z;
        this.mApp.getWindowProcessController().setHasOverlayUi(z);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean hasOverlayUi() {
        return this.mHasOverlayUi;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isRunningRemoteAnimation() {
        return this.mRunningRemoteAnimation;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setRunningRemoteAnimation(boolean z) {
        if (this.mRunningRemoteAnimation == z) {
            return;
        }
        this.mRunningRemoteAnimation = z;
        this.mService.updateOomAdjLocked(this.mApp, 9);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setProcStateChanged(boolean z) {
        this.mProcStateChanged = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean hasProcStateChanged() {
        return this.mProcStateChanged;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setReportedInteraction(boolean z) {
        this.mReportedInteraction = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean hasReportedInteraction() {
        return this.mReportedInteraction;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setInteractionEventTime(long j) {
        this.mInteractionEventTime = j;
        this.mApp.getWindowProcessController().setInteractionEventTime(j);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    long getInteractionEventTime() {
        return this.mInteractionEventTime;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setFgInteractionTime(long j) {
        this.mFgInteractionTime = j;
        this.mApp.getWindowProcessController().setFgInteractionTime(j);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    long getFgInteractionTime() {
        return this.mFgInteractionTime;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setForcingToImportant(java.lang.Object obj) {
        this.mForcingToImportant = obj;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    java.lang.Object getForcingToImportant() {
        return this.mForcingToImportant;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setAdjSeq(int i) {
        this.mAdjSeq = i;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void decAdjSeq() {
        this.mAdjSeq--;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getAdjSeq() {
        return this.mAdjSeq;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setCompletedAdjSeq(int i) {
        this.mCompletedAdjSeq = i;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void decCompletedAdjSeq() {
        this.mCompletedAdjSeq--;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getCompletedAdjSeq() {
        return this.mCompletedAdjSeq;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setContainsCycle(boolean z) {
        this.mContainsCycle = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean containsCycle() {
        return this.mContainsCycle;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setWhenUnimportant(long j) {
        this.mWhenUnimportant = j;
        this.mApp.getWindowProcessController().setWhenUnimportant(j);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    long getWhenUnimportant() {
        return this.mWhenUnimportant;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setLastTopTime(long j) {
        this.mLastTopTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    long getLastTopTime() {
        return this.mLastTopTime;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isEmpty() {
        return this.mCurProcState >= 19;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isCached() {
        return this.mCurAdj >= 900;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getCacheOomRankerUseCount() {
        return this.mCacheOomRankerUseCount;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setSystemNoUi(boolean z) {
        this.mSystemNoUi = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isSystemNoUi() {
        return this.mSystemNoUi;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setAdjType(java.lang.String str) {
        this.mAdjType = str;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    java.lang.String getAdjType() {
        return this.mAdjType;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setAdjTypeCode(int i) {
        this.mAdjTypeCode = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getAdjTypeCode() {
        return this.mAdjTypeCode;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setAdjSource(java.lang.Object obj) {
        this.mAdjSource = obj;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    java.lang.Object getAdjSource() {
        return this.mAdjSource;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setAdjSourceProcState(int i) {
        this.mAdjSourceProcState = i;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getAdjSourceProcState() {
        return this.mAdjSourceProcState;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setAdjTarget(java.lang.Object obj) {
        this.mAdjTarget = obj;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    java.lang.Object getAdjTarget() {
        return this.mAdjTarget;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isReachable() {
        return this.mReachable;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setReachable(boolean z) {
        this.mReachable = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void resetCachedInfo() {
        this.mCachedHasActivities = -1;
        this.mCachedIsHeavyWeight = -1;
        this.mCachedHasVisibleActivities = -1;
        this.mCachedIsHomeProcess = -1;
        this.mCachedIsPreviousProcess = -1;
        this.mCachedHasRecentTasks = -1;
        this.mCachedIsReceivingBroadcast = -1;
        this.mCachedAdj = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mCachedForegroundActivities = false;
        this.mCachedProcState = 19;
        this.mCachedSchedGroup = 0;
        this.mCachedAdjType = null;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean getCachedHasActivities() {
        if (this.mCachedHasActivities == -1) {
            this.mCachedHasActivities = this.mApp.getWindowProcessController().hasActivities() ? 1 : 0;
            if (this.mCachedHasActivities == 1) {
                this.mApp.mProfile.addHostingComponentType(16);
            } else {
                this.mApp.mProfile.clearHostingComponentType(16);
            }
        }
        return this.mCachedHasActivities == 1;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean getCachedIsHeavyWeight() {
        if (this.mCachedIsHeavyWeight == -1) {
            this.mCachedIsHeavyWeight = this.mApp.getWindowProcessController().isHeavyWeightProcess() ? 1 : 0;
        }
        return this.mCachedIsHeavyWeight == 1;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean getCachedHasVisibleActivities() {
        if (this.mCachedHasVisibleActivities == -1) {
            this.mCachedHasVisibleActivities = this.mApp.getWindowProcessController().hasVisibleActivities() ? 1 : 0;
        }
        return this.mCachedHasVisibleActivities == 1;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean getCachedIsHomeProcess() {
        if (this.mCachedIsHomeProcess == -1) {
            if (this.mApp.getWindowProcessController().isHomeProcess()) {
                this.mCachedIsHomeProcess = 1;
                this.mService.mAppProfiler.mHasHomeProcess = true;
            } else {
                this.mCachedIsHomeProcess = 0;
            }
        }
        return this.mCachedIsHomeProcess == 1;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean getCachedIsPreviousProcess() {
        if (this.mCachedIsPreviousProcess == -1) {
            if (this.mApp.getWindowProcessController().isPreviousProcess()) {
                this.mCachedIsPreviousProcess = 1;
                this.mService.mAppProfiler.mHasPreviousProcess = true;
            } else {
                this.mCachedIsPreviousProcess = 0;
            }
        }
        return this.mCachedIsPreviousProcess == 1;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean getCachedHasRecentTasks() {
        if (this.mCachedHasRecentTasks == -1) {
            this.mCachedHasRecentTasks = this.mApp.getWindowProcessController().hasRecentTasks() ? 1 : 0;
        }
        return this.mCachedHasRecentTasks == 1;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean getCachedIsReceivingBroadcast(int[] iArr) {
        if (this.mCachedIsReceivingBroadcast == -1) {
            this.mCachedIsReceivingBroadcast = this.mService.isReceivingBroadcastLocked(this.mApp, iArr) ? 1 : 0;
            if (this.mCachedIsReceivingBroadcast == 1) {
                this.mCachedSchedGroup = iArr[0];
                this.mApp.mProfile.addHostingComponentType(32);
            } else {
                this.mApp.mProfile.clearHostingComponentType(32);
            }
        }
        return this.mCachedIsReceivingBroadcast == 1;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean getCachedCompatChange(int i) {
        if (this.mCachedCompatChanges[i] == -1) {
            this.mCachedCompatChanges[i] = this.mService.mOomAdjuster.isChangeEnabled(i, this.mApp.info, false) ? 1 : 0;
        }
        return this.mCachedCompatChanges[i] == 1;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void computeOomAdjFromActivitiesIfNecessary(com.android.server.am.OomAdjuster.ComputeOomAdjWindowCallback computeOomAdjWindowCallback, int i, boolean z, boolean z2, int i2, int i3, int i4, int i5, int i6) {
        if (this.mCachedAdj != -10000) {
            return;
        }
        computeOomAdjWindowCallback.initialize(this.mApp, i, z, z2, i2, i3, i4, i5, i6);
        int min = java.lang.Math.min(99, this.mApp.getWindowProcessController().computeOomAdjFromActivities(computeOomAdjWindowCallback));
        this.mCachedAdj = computeOomAdjWindowCallback.adj;
        this.mCachedForegroundActivities = computeOomAdjWindowCallback.foregroundActivities;
        this.mCachedHasVisibleActivities = computeOomAdjWindowCallback.mHasVisibleActivities ? 1 : 0;
        this.mCachedProcState = computeOomAdjWindowCallback.procState;
        this.mCachedSchedGroup = computeOomAdjWindowCallback.schedGroup;
        this.mCachedAdjType = computeOomAdjWindowCallback.mAdjType;
        if (this.mCachedAdj == 100) {
            this.mCachedAdj += min;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getCachedAdj() {
        return this.mCachedAdj;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean getCachedForegroundActivities() {
        return this.mCachedForegroundActivities;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getCachedProcState() {
        return this.mCachedProcState;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getCachedSchedGroup() {
        return this.mCachedSchedGroup;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    java.lang.String getCachedAdjType() {
        return this.mCachedAdjType;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean shouldScheduleLikeTopApp() {
        return this.mScheduleLikeTopApp;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setScheduleLikeTopApp(boolean z) {
        this.mScheduleLikeTopApp = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    public java.lang.String makeAdjReason() {
        if (this.mAdjSource != null || this.mAdjTarget != null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append(' ');
            if (this.mAdjTarget instanceof android.content.ComponentName) {
                sb.append(((android.content.ComponentName) this.mAdjTarget).flattenToShortString());
            } else if (this.mAdjTarget != null) {
                sb.append(this.mAdjTarget.toString());
            } else {
                sb.append("{null}");
            }
            sb.append("<=");
            if (this.mAdjSource instanceof com.android.server.am.ProcessRecord) {
                sb.append("Proc{");
                sb.append(((com.android.server.am.ProcessRecord) this.mAdjSource).toShortString());
                sb.append("}");
            } else if (this.mAdjSource != null) {
                sb.append(this.mAdjSource.toString());
            } else {
                sb.append("{null}");
            }
            return sb.toString();
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void onCleanupApplicationRecordLSP() {
        setHasForegroundActivities(false);
        this.mHasShownUi = false;
        this.mForcingToImportant = null;
        this.mVerifiedAdj = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mSetAdj = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mCurAdj = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mSetRawAdj = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mCurRawAdj = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mSetCapability = 0;
        this.mCurCapability = 0;
        this.mSetSchedGroup = 0;
        this.mCurSchedGroup = 0;
        this.mSetProcState = 20;
        this.mCurRawProcState = 20;
        this.mCurProcState = 20;
        for (int i = 0; i < this.mCachedCompatChanges.length; i++) {
            this.mCachedCompatChanges[i] = -1;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isBackgroundRestricted() {
        return this.mBackgroundRestricted;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setBackgroundRestricted(boolean z) {
        this.mBackgroundRestricted = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isCurBoundByNonBgRestrictedApp() {
        return this.mCurBoundByNonBgRestrictedApp;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setCurBoundByNonBgRestrictedApp(boolean z) {
        this.mCurBoundByNonBgRestrictedApp = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isSetBoundByNonBgRestrictedApp() {
        return this.mSetBoundByNonBgRestrictedApp;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setSetBoundByNonBgRestrictedApp(boolean z) {
        this.mSetBoundByNonBgRestrictedApp = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void updateLastInvisibleTime(boolean z) {
        if (z) {
            this.mLastInvisibleTime = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        } else if (this.mLastInvisibleTime == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            this.mLastInvisibleTime = android.os.SystemClock.elapsedRealtime();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    long getLastInvisibleTime() {
        return this.mLastInvisibleTime;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setNoKillOnBgRestrictedAndIdle(boolean z) {
        this.mNoKillOnBgRestrictedAndIdle = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean shouldNotKillOnBgRestrictedAndIdle() {
        return this.mNoKillOnBgRestrictedAndIdle;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setSetCached(boolean z) {
        this.mSetCached = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isSetCached() {
        return this.mSetCached;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setSetNoKillOnBgRestrictedAndIdle(boolean z) {
        this.mSetNoKillOnBgRestrictedAndIdle = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isSetNoKillOnBgRestrictedAndIdle() {
        return this.mSetNoKillOnBgRestrictedAndIdle;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setLastCanKillOnBgRestrictedAndIdleTime(long j) {
        this.mLastCanKillOnBgRestrictedAndIdleTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    long getLastCanKillOnBgRestrictedAndIdleTime() {
        return this.mLastCanKillOnBgRestrictedAndIdleTime;
    }

    public void setCacheOomRankerRss(long j, long j2) {
        this.mCacheOomRankerRss = j;
        this.mCacheOomRankerRssTimeMs = j2;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    public long getCacheOomRankerRss() {
        return this.mCacheOomRankerRss;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    public long getCacheOomRankerRssTimeMs() {
        return this.mCacheOomRankerRssTimeMs;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void dump(java.io.PrintWriter printWriter, java.lang.String str, long j) {
        if (this.mReportedInteraction || this.mFgInteractionTime != 0) {
            printWriter.print(str);
            printWriter.print("reportedInteraction=");
            printWriter.print(this.mReportedInteraction);
            if (this.mInteractionEventTime != 0) {
                printWriter.print(" time=");
                android.util.TimeUtils.formatDuration(this.mInteractionEventTime, android.os.SystemClock.elapsedRealtime(), printWriter);
            }
            if (this.mFgInteractionTime != 0) {
                printWriter.print(" fgInteractionTime=");
                android.util.TimeUtils.formatDuration(this.mFgInteractionTime, android.os.SystemClock.elapsedRealtime(), printWriter);
            }
            printWriter.println();
        }
        printWriter.print(str);
        printWriter.print("adjSeq=");
        printWriter.print(this.mAdjSeq);
        printWriter.print(" lruSeq=");
        printWriter.println(this.mApp.getLruSeq());
        printWriter.print(str);
        printWriter.print("oom adj: max=");
        printWriter.print(this.mMaxAdj);
        printWriter.print(" curRaw=");
        printWriter.print(this.mCurRawAdj);
        printWriter.print(" setRaw=");
        printWriter.print(this.mSetRawAdj);
        printWriter.print(" cur=");
        printWriter.print(this.mCurAdj);
        printWriter.print(" set=");
        printWriter.println(this.mSetAdj);
        printWriter.print(str);
        printWriter.print("mCurSchedGroup=");
        printWriter.print(this.mCurSchedGroup);
        printWriter.print(" setSchedGroup=");
        printWriter.print(this.mSetSchedGroup);
        printWriter.print(" systemNoUi=");
        printWriter.println(this.mSystemNoUi);
        printWriter.print(str);
        printWriter.print("curProcState=");
        printWriter.print(getCurProcState());
        printWriter.print(" mRepProcState=");
        printWriter.print(this.mRepProcState);
        printWriter.print(" setProcState=");
        printWriter.print(this.mSetProcState);
        printWriter.print(" lastStateTime=");
        android.util.TimeUtils.formatDuration(getLastStateTime(), j, printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("curCapability=");
        android.app.ActivityManager.printCapabilitiesFull(printWriter, this.mCurCapability);
        printWriter.print(" setCapability=");
        android.app.ActivityManager.printCapabilitiesFull(printWriter, this.mSetCapability);
        printWriter.println();
        if (this.mBackgroundRestricted) {
            printWriter.print(" backgroundRestricted=");
            printWriter.print(this.mBackgroundRestricted);
            printWriter.print(" boundByNonBgRestrictedApp=");
            printWriter.print(this.mSetBoundByNonBgRestrictedApp);
        }
        printWriter.println();
        if (this.mHasShownUi || this.mApp.mProfile.hasPendingUiClean()) {
            printWriter.print(str);
            printWriter.print("hasShownUi=");
            printWriter.print(this.mHasShownUi);
            printWriter.print(" pendingUiClean=");
            printWriter.println(this.mApp.mProfile.hasPendingUiClean());
        }
        printWriter.print(str);
        printWriter.print("cached=");
        printWriter.print(isCached());
        printWriter.print(" empty=");
        printWriter.println(isEmpty());
        if (this.mServiceB) {
            printWriter.print(str);
            printWriter.print("serviceb=");
            printWriter.print(this.mServiceB);
            printWriter.print(" serviceHighRam=");
            printWriter.println(this.mServiceHighRam);
        }
        if (this.mNotCachedSinceIdle) {
            printWriter.print(str);
            printWriter.print("notCachedSinceIdle=");
            printWriter.print(this.mNotCachedSinceIdle);
            if (this.mService.mAppProfiler.isProfilingPss()) {
                printWriter.print(" initialIdlePss=");
            } else {
                printWriter.print(" initialIdleRss=");
            }
            printWriter.println(this.mApp.mProfile.getInitialIdlePssOrRss());
        }
        if (hasTopUi() || hasOverlayUi() || this.mRunningRemoteAnimation) {
            printWriter.print(str);
            printWriter.print("hasTopUi=");
            printWriter.print(hasTopUi());
            printWriter.print(" hasOverlayUi=");
            printWriter.print(hasOverlayUi());
            printWriter.print(" runningRemoteAnimation=");
            printWriter.println(this.mRunningRemoteAnimation);
        }
        if (this.mHasForegroundActivities || this.mRepForegroundActivities) {
            printWriter.print(str);
            printWriter.print("foregroundActivities=");
            printWriter.print(this.mHasForegroundActivities);
            printWriter.print(" (rep=");
            printWriter.print(this.mRepForegroundActivities);
            printWriter.println(")");
        }
        if (this.mSetProcState > 10) {
            printWriter.print(str);
            printWriter.print("whenUnimportant=");
            android.util.TimeUtils.formatDuration(this.mWhenUnimportant - j, printWriter);
            printWriter.println();
        }
        if (this.mLastTopTime > 0) {
            printWriter.print(str);
            printWriter.print("lastTopTime=");
            android.util.TimeUtils.formatDuration(this.mLastTopTime, j, printWriter);
            printWriter.println();
        }
        if (this.mLastInvisibleTime > 0 && this.mLastInvisibleTime < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            printWriter.print(str);
            printWriter.print("lastInvisibleTime=");
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            android.util.TimeUtils.dumpTimeWithDelta(printWriter, (currentTimeMillis - elapsedRealtime) + this.mLastInvisibleTime, currentTimeMillis);
            printWriter.println();
        }
        if (this.mHasStartedServices) {
            printWriter.print(str);
            printWriter.print("hasStartedServices=");
            printWriter.println(this.mHasStartedServices);
        }
    }
}
