package com.android.server.am;

/* loaded from: classes.dex */
class BroadcastProcessQueue {
    static final int REASON_BLOCKED = 4;
    static final int REASON_CACHED = 1;
    static final int REASON_CACHED_INFINITE_DEFER = 8;
    static final int REASON_CONTAINS_ALARM = 12;
    static final int REASON_CONTAINS_FOREGROUND = 10;
    static final int REASON_CONTAINS_INSTRUMENTED = 16;
    static final int REASON_CONTAINS_INTERACTIVE = 14;
    static final int REASON_CONTAINS_MANIFEST = 17;
    static final int REASON_CONTAINS_ORDERED = 11;
    static final int REASON_CONTAINS_PRIORITIZED = 13;
    static final int REASON_CONTAINS_RESULT_TO = 15;
    static final int REASON_CORE_UID = 19;
    static final int REASON_EMPTY = 0;
    static final int REASON_FORCE_DELAYED = 7;
    static final int REASON_FOREGROUND = 18;
    static final int REASON_INSTRUMENTED = 5;
    static final int REASON_MAX_PENDING = 3;
    static final int REASON_NORMAL = 2;
    static final int REASON_PERSISTENT = 6;
    static final int REASON_TOP_PROCESS = 20;
    static final boolean VERBOSE = false;

    @android.annotation.Nullable
    com.android.server.am.ProcessRecord app;

    @android.annotation.NonNull
    final com.android.server.am.BroadcastConstants constants;
    long lastCpuDelayTime;
    int lastProcessState;

    @android.annotation.Nullable
    private com.android.server.am.BroadcastRecord mActive;
    private int mActiveAssumedDeliveryCountSinceIdle;
    private int mActiveCountConsecutiveNormal;
    private int mActiveCountConsecutiveUrgent;
    private int mActiveCountSinceIdle;
    private int mActiveIndex;
    private boolean mActiveReEnqueued;
    private boolean mActiveViaColdStart;
    private boolean mActiveWasStopped;
    private java.lang.String mCachedToShortString;
    private java.lang.String mCachedToString;
    private int mCountAlarm;
    private int mCountDeferred;
    private int mCountEnqueued;
    private int mCountForeground;
    private int mCountForegroundDeferred;
    private int mCountInstrumented;
    private int mCountInteractive;
    private int mCountManifest;
    private int mCountOrdered;
    private int mCountPrioritizeEarliestRequests;
    private int mCountPrioritized;
    private int mCountPrioritizedDeferred;
    private int mCountResultTo;
    private long mForcedDelayedDurationMs;
    private boolean mLastDeferredStates;
    private boolean mProcessFreezable;
    private boolean mProcessInstrumented;
    private boolean mProcessPersistent;
    private boolean mRunnableAtInvalidated;
    private boolean mTimeoutScheduled;
    private boolean mUidForeground;

    @android.annotation.NonNull
    final java.lang.String processName;

    @android.annotation.Nullable
    com.android.server.am.BroadcastProcessQueue processNameNext;

    @android.annotation.Nullable
    com.android.server.am.BroadcastProcessQueue runnableAtNext;

    @android.annotation.Nullable
    com.android.server.am.BroadcastProcessQueue runnableAtPrev;
    boolean runningOomAdjusted;

    @android.annotation.Nullable
    java.lang.String runningTraceTrackName;
    final int uid;
    private final java.util.ArrayDeque<com.android.internal.os.SomeArgs> mPending = new java.util.ArrayDeque<>();
    private final java.util.ArrayDeque<com.android.internal.os.SomeArgs> mPendingUrgent = new java.util.ArrayDeque<>(4);
    private final java.util.ArrayDeque<com.android.internal.os.SomeArgs> mPendingOffload = new java.util.ArrayDeque<>(4);
    private long mRunnableAt = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
    private int mRunnableAtReason = 0;

    @java.lang.FunctionalInterface
    public interface BroadcastConsumer {
        void accept(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i);
    }

    @java.lang.FunctionalInterface
    public interface BroadcastPredicate {
        boolean test(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Reason {
    }

    public BroadcastProcessQueue(@android.annotation.NonNull com.android.server.am.BroadcastConstants broadcastConstants, @android.annotation.NonNull java.lang.String str, int i) {
        java.util.Objects.requireNonNull(broadcastConstants);
        this.constants = broadcastConstants;
        java.util.Objects.requireNonNull(str);
        this.processName = str;
        this.uid = i;
    }

    @android.annotation.NonNull
    private java.util.ArrayDeque<com.android.internal.os.SomeArgs> getQueueForBroadcast(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord) {
        if (broadcastRecord.isUrgent()) {
            return this.mPendingUrgent;
        }
        if (broadcastRecord.isOffload()) {
            return this.mPendingOffload;
        }
        return this.mPending;
    }

    @android.annotation.Nullable
    public com.android.server.am.BroadcastRecord enqueueOrReplaceBroadcast(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i, @android.annotation.NonNull com.android.server.am.BroadcastProcessQueue.BroadcastConsumer broadcastConsumer) {
        com.android.server.am.BroadcastRecord replaceBroadcast;
        if (broadcastRecord.isReplacePending() && broadcastRecord.getDeliveryGroupPolicy() == 0 && (replaceBroadcast = replaceBroadcast(broadcastRecord, i)) != null) {
            return replaceBroadcast;
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = broadcastRecord;
        obtain.argi1 = i;
        getQueueForBroadcast(broadcastRecord).addLast(obtain);
        onBroadcastEnqueued(broadcastRecord, i);
        if (this.mLastDeferredStates && shouldBeDeferred() && broadcastRecord.getDeliveryState(i) == 0) {
            broadcastConsumer.accept(broadcastRecord, i);
            return null;
        }
        return null;
    }

    public void reEnqueueActiveBroadcast() {
        com.android.server.am.BroadcastRecord active = getActive();
        int activeIndex = getActiveIndex();
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = active;
        obtain.argi1 = activeIndex;
        obtain.argi2 = 1;
        getQueueForBroadcast(active).addFirst(obtain);
        onBroadcastEnqueued(active, activeIndex);
    }

    @android.annotation.Nullable
    private com.android.server.am.BroadcastRecord replaceBroadcast(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        return replaceBroadcastInQueue(getQueueForBroadcast(broadcastRecord), broadcastRecord, i);
    }

    @android.annotation.Nullable
    private com.android.server.am.BroadcastRecord replaceBroadcastInQueue(@android.annotation.NonNull java.util.ArrayDeque<com.android.internal.os.SomeArgs> arrayDeque, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        java.util.Iterator<com.android.internal.os.SomeArgs> descendingIterator = arrayDeque.descendingIterator();
        java.lang.Object obj = broadcastRecord.receivers.get(i);
        while (descendingIterator.hasNext()) {
            com.android.internal.os.SomeArgs next = descendingIterator.next();
            com.android.server.am.BroadcastRecord broadcastRecord2 = (com.android.server.am.BroadcastRecord) next.arg1;
            int i2 = next.argi1;
            java.lang.Object obj2 = broadcastRecord2.receivers.get(i2);
            if (broadcastRecord != broadcastRecord2) {
                if (broadcastRecord.callingUid == broadcastRecord2.callingUid && broadcastRecord.userId == broadcastRecord2.userId && broadcastRecord.intent.filterEquals(broadcastRecord2.intent) && com.android.server.am.BroadcastRecord.isReceiverEquals(obj, obj2) && broadcastRecord2.allReceiversPending() && broadcastRecord.isMatchingRecord(broadcastRecord2)) {
                    next.arg1 = broadcastRecord;
                    next.argi1 = i;
                    broadcastRecord.copyEnqueueTimeFrom(broadcastRecord2);
                    onBroadcastDequeued(broadcastRecord2, i2);
                    onBroadcastEnqueued(broadcastRecord, i);
                    return broadcastRecord2;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    public boolean forEachMatchingBroadcast(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue.BroadcastPredicate broadcastPredicate, @android.annotation.NonNull com.android.server.am.BroadcastProcessQueue.BroadcastConsumer broadcastConsumer, boolean z) {
        return forEachMatchingBroadcastInQueue(this.mPendingOffload, broadcastPredicate, broadcastConsumer, z) | forEachMatchingBroadcastInQueue(this.mPending, broadcastPredicate, broadcastConsumer, z) | false | forEachMatchingBroadcastInQueue(this.mPendingUrgent, broadcastPredicate, broadcastConsumer, z);
    }

    private boolean forEachMatchingBroadcastInQueue(@android.annotation.NonNull java.util.ArrayDeque<com.android.internal.os.SomeArgs> arrayDeque, @android.annotation.NonNull com.android.server.am.BroadcastProcessQueue.BroadcastPredicate broadcastPredicate, @android.annotation.NonNull com.android.server.am.BroadcastProcessQueue.BroadcastConsumer broadcastConsumer, boolean z) {
        java.util.Iterator<com.android.internal.os.SomeArgs> it = arrayDeque.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            com.android.internal.os.SomeArgs next = it.next();
            com.android.server.am.BroadcastRecord broadcastRecord = (com.android.server.am.BroadcastRecord) next.arg1;
            int i = next.argi1;
            if (broadcastPredicate.test(broadcastRecord, i)) {
                broadcastConsumer.accept(broadcastRecord, i);
                if (z) {
                    next.recycle();
                    it.remove();
                    onBroadcastDequeued(broadcastRecord, i);
                } else {
                    invalidateRunnableAt();
                }
                z2 = true;
            }
        }
        return z2;
    }

    public boolean setProcessAndUidState(@android.annotation.Nullable com.android.server.am.ProcessRecord processRecord, boolean z, boolean z2) {
        this.app = processRecord;
        this.mCachedToString = null;
        this.mCachedToShortString = null;
        if (processRecord != null) {
            return setProcessPersistent(processRecord.isPersistent()) | setUidForeground(z) | false | setProcessFreezable(z2) | setProcessInstrumented(processRecord.getActiveInstrumentation() != null);
        }
        return setUidForeground(false) | false | setProcessFreezable(false) | setProcessInstrumented(false) | setProcessPersistent(false);
    }

    private boolean setUidForeground(boolean z) {
        if (this.mUidForeground != z) {
            this.mUidForeground = z;
            invalidateRunnableAt();
            return true;
        }
        return false;
    }

    private boolean setProcessFreezable(boolean z) {
        if (this.mProcessFreezable != z) {
            this.mProcessFreezable = z;
            invalidateRunnableAt();
            return true;
        }
        return false;
    }

    private boolean setProcessInstrumented(boolean z) {
        if (this.mProcessInstrumented != z) {
            this.mProcessInstrumented = z;
            invalidateRunnableAt();
            return true;
        }
        return false;
    }

    private boolean setProcessPersistent(boolean z) {
        if (this.mProcessPersistent != z) {
            this.mProcessPersistent = z;
            invalidateRunnableAt();
            return true;
        }
        return false;
    }

    public boolean isProcessWarm() {
        return (this.app == null || this.app.getOnewayThread() == null || this.app.isKilled()) ? false : true;
    }

    public int getPreferredSchedulingGroupLocked() {
        if (!isActive()) {
            return Integer.MIN_VALUE;
        }
        if (this.mCountForeground > this.mCountForegroundDeferred) {
            return 2;
        }
        return (this.mActive == null || !this.mActive.isForeground()) ? 0 : 2;
    }

    public int getActiveCountSinceIdle() {
        return this.mActiveCountSinceIdle;
    }

    public int getActiveAssumedDeliveryCountSinceIdle() {
        return this.mActiveAssumedDeliveryCountSinceIdle;
    }

    public void setActiveViaColdStart(boolean z) {
        this.mActiveViaColdStart = z;
    }

    public void setActiveWasStopped(boolean z) {
        this.mActiveWasStopped = z;
    }

    public boolean getActiveViaColdStart() {
        return this.mActiveViaColdStart;
    }

    public boolean getActiveWasStopped() {
        return this.mActiveWasStopped;
    }

    @android.annotation.Nullable
    public java.lang.String getPackageName() {
        if (this.app == null) {
            return null;
        }
        return this.app.getApplicationInfo().packageName;
    }

    public void makeActiveNextPending() {
        com.android.internal.os.SomeArgs removeNextBroadcast = removeNextBroadcast();
        this.mActive = (com.android.server.am.BroadcastRecord) removeNextBroadcast.arg1;
        this.mActiveIndex = removeNextBroadcast.argi1;
        this.mActiveReEnqueued = removeNextBroadcast.argi2 == 1;
        this.mActiveCountSinceIdle++;
        this.mActiveAssumedDeliveryCountSinceIdle += this.mActive.isAssumedDelivered(this.mActiveIndex) ? 1 : 0;
        this.mActiveViaColdStart = false;
        this.mActiveWasStopped = false;
        removeNextBroadcast.recycle();
        onBroadcastDequeued(this.mActive, this.mActiveIndex);
    }

    public void makeActiveIdle() {
        this.mActive = null;
        this.mActiveIndex = 0;
        this.mActiveReEnqueued = false;
        this.mActiveCountSinceIdle = 0;
        this.mActiveAssumedDeliveryCountSinceIdle = 0;
        this.mActiveViaColdStart = false;
        invalidateRunnableAt();
    }

    public boolean wasActiveBroadcastReEnqueued() {
        com.android.server.am.Flags.avoidRepeatedBcastReEnqueues();
        return false;
    }

    private void onBroadcastEnqueued(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        this.mCountEnqueued++;
        if (broadcastRecord.deferUntilActive) {
            this.mCountDeferred++;
        }
        if (broadcastRecord.isForeground()) {
            if (broadcastRecord.deferUntilActive) {
                this.mCountForegroundDeferred++;
            }
            this.mCountForeground++;
        }
        if (broadcastRecord.ordered) {
            this.mCountOrdered++;
        }
        if (broadcastRecord.alarm) {
            this.mCountAlarm++;
        }
        if (broadcastRecord.prioritized) {
            if (broadcastRecord.deferUntilActive) {
                this.mCountPrioritizedDeferred++;
            }
            this.mCountPrioritized++;
        }
        if (broadcastRecord.interactive) {
            this.mCountInteractive++;
        }
        if (broadcastRecord.resultTo != null) {
            this.mCountResultTo++;
        }
        if (broadcastRecord.callerInstrumented) {
            this.mCountInstrumented++;
        }
        if (broadcastRecord.receivers.get(i) instanceof android.content.pm.ResolveInfo) {
            this.mCountManifest++;
        }
        invalidateRunnableAt();
    }

    private void onBroadcastDequeued(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        this.mCountEnqueued--;
        if (broadcastRecord.deferUntilActive) {
            this.mCountDeferred--;
        }
        if (broadcastRecord.isForeground()) {
            if (broadcastRecord.deferUntilActive) {
                this.mCountForegroundDeferred--;
            }
            this.mCountForeground--;
        }
        if (broadcastRecord.ordered) {
            this.mCountOrdered--;
        }
        if (broadcastRecord.alarm) {
            this.mCountAlarm--;
        }
        if (broadcastRecord.prioritized) {
            if (broadcastRecord.deferUntilActive) {
                this.mCountPrioritizedDeferred--;
            }
            this.mCountPrioritized--;
        }
        if (broadcastRecord.interactive) {
            this.mCountInteractive--;
        }
        if (broadcastRecord.resultTo != null) {
            this.mCountResultTo--;
        }
        if (broadcastRecord.callerInstrumented) {
            this.mCountInstrumented--;
        }
        if (broadcastRecord.receivers.get(i) instanceof android.content.pm.ResolveInfo) {
            this.mCountManifest--;
        }
        invalidateRunnableAt();
    }

    public void traceProcessStartingBegin() {
        android.os.Trace.asyncTraceForTrackBegin(64L, this.runningTraceTrackName, toShortString() + " starting", hashCode());
    }

    public void traceProcessRunningBegin() {
        android.os.Trace.asyncTraceForTrackBegin(64L, this.runningTraceTrackName, toShortString() + " running", hashCode());
    }

    public void traceProcessEnd() {
        android.os.Trace.asyncTraceForTrackEnd(64L, this.runningTraceTrackName, hashCode());
    }

    public void traceActiveBegin() {
        android.os.Trace.asyncTraceForTrackBegin(64L, this.runningTraceTrackName, this.mActive.toShortString() + " scheduled", hashCode());
    }

    public void traceActiveEnd() {
        android.os.Trace.asyncTraceForTrackEnd(64L, this.runningTraceTrackName, hashCode());
    }

    @android.annotation.NonNull
    public com.android.server.am.BroadcastRecord getActive() {
        com.android.server.am.BroadcastRecord broadcastRecord = this.mActive;
        java.util.Objects.requireNonNull(broadcastRecord);
        return broadcastRecord;
    }

    public int getActiveIndex() {
        java.util.Objects.requireNonNull(this.mActive);
        return this.mActiveIndex;
    }

    public boolean isEmpty() {
        return this.mPending.isEmpty() && this.mPendingUrgent.isEmpty() && this.mPendingOffload.isEmpty();
    }

    public boolean isActive() {
        return this.mActive != null;
    }

    boolean forceDelayBroadcastDelivery(long j) {
        if (this.mForcedDelayedDurationMs != j) {
            this.mForcedDelayedDurationMs = j;
            invalidateRunnableAt();
            return true;
        }
        return false;
    }

    @android.annotation.Nullable
    private com.android.internal.os.SomeArgs removeNextBroadcast() {
        java.util.ArrayDeque<com.android.internal.os.SomeArgs> queueForNextBroadcast = queueForNextBroadcast();
        if (queueForNextBroadcast == this.mPendingUrgent) {
            this.mActiveCountConsecutiveUrgent++;
        } else if (queueForNextBroadcast == this.mPending) {
            this.mActiveCountConsecutiveUrgent = 0;
            this.mActiveCountConsecutiveNormal++;
        } else if (queueForNextBroadcast == this.mPendingOffload) {
            this.mActiveCountConsecutiveUrgent = 0;
            this.mActiveCountConsecutiveNormal = 0;
        }
        if (isQueueEmpty(queueForNextBroadcast)) {
            return null;
        }
        return queueForNextBroadcast.removeFirst();
    }

    @android.annotation.Nullable
    java.util.ArrayDeque<com.android.internal.os.SomeArgs> queueForNextBroadcast() {
        return queueForNextBroadcast(this.mPendingUrgent, queueForNextBroadcast(this.mPending, this.mPendingOffload, this.mActiveCountConsecutiveNormal, this.constants.MAX_CONSECUTIVE_NORMAL_DISPATCHES), this.mActiveCountConsecutiveUrgent, this.constants.MAX_CONSECUTIVE_URGENT_DISPATCHES);
    }

    @android.annotation.Nullable
    private java.util.ArrayDeque<com.android.internal.os.SomeArgs> queueForNextBroadcast(@android.annotation.Nullable java.util.ArrayDeque<com.android.internal.os.SomeArgs> arrayDeque, @android.annotation.Nullable java.util.ArrayDeque<com.android.internal.os.SomeArgs> arrayDeque2, int i, int i2) {
        if (isQueueEmpty(arrayDeque)) {
            return arrayDeque2;
        }
        if (isQueueEmpty(arrayDeque2)) {
            return arrayDeque;
        }
        com.android.internal.os.SomeArgs peekFirst = arrayDeque2.peekFirst();
        com.android.server.am.BroadcastRecord broadcastRecord = (com.android.server.am.BroadcastRecord) peekFirst.arg1;
        return (this.mCountPrioritizeEarliestRequests > 0 || i >= i2) && broadcastRecord.enqueueTime <= ((com.android.server.am.BroadcastRecord) arrayDeque.peekFirst().arg1).enqueueTime && !broadcastRecord.isBlocked(peekFirst.argi1) ? arrayDeque2 : arrayDeque;
    }

    private static boolean isQueueEmpty(@android.annotation.Nullable java.util.ArrayDeque<com.android.internal.os.SomeArgs> arrayDeque) {
        return arrayDeque == null || arrayDeque.isEmpty();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean addPrioritizeEarliestRequest() {
        if (this.mCountPrioritizeEarliestRequests == 0) {
            this.mCountPrioritizeEarliestRequests++;
            invalidateRunnableAt();
            return true;
        }
        this.mCountPrioritizeEarliestRequests++;
        return false;
    }

    boolean removePrioritizeEarliestRequest() {
        this.mCountPrioritizeEarliestRequests--;
        if (this.mCountPrioritizeEarliestRequests == 0) {
            invalidateRunnableAt();
            return true;
        }
        if (this.mCountPrioritizeEarliestRequests >= 0) {
            return false;
        }
        this.mCountPrioritizeEarliestRequests = 0;
        return false;
    }

    @android.annotation.Nullable
    com.android.internal.os.SomeArgs peekNextBroadcast() {
        java.util.ArrayDeque<com.android.internal.os.SomeArgs> queueForNextBroadcast = queueForNextBroadcast();
        if (isQueueEmpty(queueForNextBroadcast)) {
            return null;
        }
        return queueForNextBroadcast.peekFirst();
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.am.BroadcastRecord peekNextBroadcastRecord() {
        java.util.ArrayDeque<com.android.internal.os.SomeArgs> queueForNextBroadcast = queueForNextBroadcast();
        if (isQueueEmpty(queueForNextBroadcast)) {
            return null;
        }
        return (com.android.server.am.BroadcastRecord) queueForNextBroadcast.peekFirst().arg1;
    }

    public boolean isPendingManifest() {
        return this.mCountManifest > 0;
    }

    public boolean isPendingOrdered() {
        return this.mCountOrdered > 0;
    }

    public boolean isPendingResultTo() {
        return this.mCountResultTo > 0;
    }

    public boolean isPendingUrgent() {
        com.android.server.am.BroadcastRecord peekNextBroadcastRecord = peekNextBroadcastRecord();
        if (peekNextBroadcastRecord != null) {
            return peekNextBroadcastRecord.isUrgent();
        }
        return false;
    }

    public boolean isIdle() {
        return (!isActive() && isEmpty()) || isDeferredUntilActive();
    }

    public boolean isBeyondBarrierLocked(long j) {
        com.android.internal.os.SomeArgs peekFirst = this.mPending.peekFirst();
        com.android.internal.os.SomeArgs peekFirst2 = this.mPendingUrgent.peekFirst();
        com.android.internal.os.SomeArgs peekFirst3 = this.mPendingOffload.peekFirst();
        return ((this.mActive == null || (this.mActive.enqueueTime > j ? 1 : (this.mActive.enqueueTime == j ? 0 : -1)) > 0) && (peekFirst == null || (((com.android.server.am.BroadcastRecord) peekFirst.arg1).enqueueTime > j ? 1 : (((com.android.server.am.BroadcastRecord) peekFirst.arg1).enqueueTime == j ? 0 : -1)) > 0) && (peekFirst2 == null || (((com.android.server.am.BroadcastRecord) peekFirst2.arg1).enqueueTime > j ? 1 : (((com.android.server.am.BroadcastRecord) peekFirst2.arg1).enqueueTime == j ? 0 : -1)) > 0) && (peekFirst3 == null || (((com.android.server.am.BroadcastRecord) peekFirst3.arg1).enqueueTime > j ? 1 : (((com.android.server.am.BroadcastRecord) peekFirst3.arg1).enqueueTime == j ? 0 : -1)) > 0)) || isDeferredUntilActive();
    }

    public boolean isDispatched(@android.annotation.NonNull android.content.Intent intent) {
        return ((this.mActive == null || !intent.filterEquals(this.mActive.intent)) && isDispatchedInQueue(this.mPending, intent) && isDispatchedInQueue(this.mPendingUrgent, intent) && isDispatchedInQueue(this.mPendingOffload, intent)) || isDeferredUntilActive();
    }

    private boolean isDispatchedInQueue(@android.annotation.NonNull java.util.ArrayDeque<com.android.internal.os.SomeArgs> arrayDeque, @android.annotation.NonNull android.content.Intent intent) {
        com.android.internal.os.SomeArgs next;
        java.util.Iterator<com.android.internal.os.SomeArgs> it = arrayDeque.iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            if (intent.filterEquals(((com.android.server.am.BroadcastRecord) next.arg1).intent)) {
                return false;
            }
        }
        return true;
    }

    public boolean isRunnable() {
        if (this.mRunnableAtInvalidated) {
            updateRunnableAt();
        }
        return this.mRunnableAt != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
    }

    public boolean isDeferredUntilActive() {
        if (this.mRunnableAtInvalidated) {
            updateRunnableAt();
        }
        return this.mRunnableAtReason == 8;
    }

    public boolean hasDeferredBroadcasts() {
        return this.mCountDeferred > 0;
    }

    public long getRunnableAt() {
        if (this.mRunnableAtInvalidated) {
            updateRunnableAt();
        }
        return this.mRunnableAt;
    }

    public int getRunnableAtReason() {
        if (this.mRunnableAtInvalidated) {
            updateRunnableAt();
        }
        return this.mRunnableAtReason;
    }

    public void invalidateRunnableAt() {
        this.mRunnableAtInvalidated = true;
    }

    @android.annotation.NonNull
    static java.lang.String reasonToString(int i) {
        switch (i) {
            case 0:
                return "EMPTY";
            case 1:
                return "CACHED";
            case 2:
                return com.android.server.utils.PriorityDump.PRIORITY_ARG_NORMAL;
            case 3:
                return "MAX_PENDING";
            case 4:
                return "BLOCKED";
            case 5:
                return "INSTRUMENTED";
            case 6:
                return "PERSISTENT";
            case 7:
                return "FORCE_DELAYED";
            case 8:
                return "INFINITE_DEFER";
            case 9:
            default:
                return java.lang.Integer.toString(i);
            case 10:
                return "CONTAINS_FOREGROUND";
            case 11:
                return "CONTAINS_ORDERED";
            case 12:
                return "CONTAINS_ALARM";
            case 13:
                return "CONTAINS_PRIORITIZED";
            case 14:
                return "CONTAINS_INTERACTIVE";
            case 15:
                return "CONTAINS_RESULT_TO";
            case 16:
                return "CONTAINS_INSTRUMENTED";
            case 17:
                return "CONTAINS_MANIFEST";
            case 18:
                return "FOREGROUND";
            case 19:
                return "CORE_UID";
            case 20:
                return "TOP_PROCESS";
        }
    }

    void updateRunnableAt() {
        if (this.mRunnableAtInvalidated) {
            this.mRunnableAtInvalidated = false;
            com.android.internal.os.SomeArgs peekNextBroadcast = peekNextBroadcast();
            if (peekNextBroadcast != null) {
                com.android.server.am.BroadcastRecord broadcastRecord = (com.android.server.am.BroadcastRecord) peekNextBroadcast.arg1;
                int i = peekNextBroadcast.argi1;
                long j = broadcastRecord.enqueueTime;
                if (broadcastRecord.isBlocked(i)) {
                    this.mRunnableAt = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                    this.mRunnableAtReason = 4;
                    return;
                }
                if (this.mForcedDelayedDurationMs > 0) {
                    this.mRunnableAt = this.mForcedDelayedDurationMs + j;
                    this.mRunnableAtReason = 7;
                } else if (this.mCountForeground > this.mCountForegroundDeferred) {
                    this.mRunnableAt = this.constants.DELAY_URGENT_MILLIS + j;
                    this.mRunnableAtReason = 10;
                } else if (this.mCountInteractive > 0) {
                    this.mRunnableAt = this.constants.DELAY_URGENT_MILLIS + j;
                    this.mRunnableAtReason = 14;
                } else if (this.mCountInstrumented > 0) {
                    this.mRunnableAt = this.constants.DELAY_URGENT_MILLIS + j;
                    this.mRunnableAtReason = 16;
                } else if (this.mProcessInstrumented) {
                    this.mRunnableAt = this.constants.DELAY_URGENT_MILLIS + j;
                    this.mRunnableAtReason = 5;
                } else if (this.mUidForeground) {
                    this.mRunnableAt = this.constants.DELAY_FOREGROUND_PROC_MILLIS + j;
                    this.mRunnableAtReason = 18;
                } else if (this.app != null && this.app.getSetProcState() == 2) {
                    this.mRunnableAt = this.constants.DELAY_FOREGROUND_PROC_MILLIS + j;
                    this.mRunnableAtReason = 20;
                } else if (this.mProcessPersistent) {
                    this.mRunnableAt = this.constants.DELAY_PERSISTENT_PROC_MILLIS + j;
                    this.mRunnableAtReason = 6;
                } else if (this.mCountOrdered > 0) {
                    this.mRunnableAt = j;
                    this.mRunnableAtReason = 11;
                } else if (this.mCountAlarm > 0) {
                    this.mRunnableAt = j;
                    this.mRunnableAtReason = 12;
                } else if (this.mCountPrioritized > this.mCountPrioritizedDeferred) {
                    this.mRunnableAt = j;
                    this.mRunnableAtReason = 13;
                } else if (this.mCountManifest > 0) {
                    this.mRunnableAt = j;
                    this.mRunnableAtReason = 17;
                } else if (this.mProcessFreezable) {
                    if (broadcastRecord.deferUntilActive) {
                        if (this.mCountDeferred == this.mCountEnqueued) {
                            this.mRunnableAt = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                            this.mRunnableAtReason = 8;
                        } else if (broadcastRecord.isForeground()) {
                            this.mRunnableAt = this.constants.DELAY_URGENT_MILLIS + j;
                            this.mRunnableAtReason = 10;
                        } else if (broadcastRecord.prioritized) {
                            this.mRunnableAt = j;
                            this.mRunnableAtReason = 13;
                        } else if (broadcastRecord.resultTo != null) {
                            this.mRunnableAt = j;
                            this.mRunnableAtReason = 15;
                        } else {
                            this.mRunnableAt = this.constants.DELAY_CACHED_MILLIS + j;
                            this.mRunnableAtReason = 1;
                        }
                    } else {
                        this.mRunnableAt = this.constants.DELAY_CACHED_MILLIS + j;
                        this.mRunnableAtReason = 1;
                    }
                } else if (this.mCountResultTo > 0) {
                    this.mRunnableAt = j;
                    this.mRunnableAtReason = 15;
                } else if (android.os.UserHandle.isCore(this.uid)) {
                    this.mRunnableAt = j;
                    this.mRunnableAtReason = 19;
                } else {
                    this.mRunnableAt = this.constants.DELAY_NORMAL_MILLIS + j;
                    this.mRunnableAtReason = 2;
                }
                if (this.mPending.size() + this.mPendingUrgent.size() + this.mPendingOffload.size() >= this.constants.MAX_PENDING_BROADCASTS) {
                    this.mRunnableAt = java.lang.Math.min(this.mRunnableAt, j);
                    this.mRunnableAtReason = 3;
                    return;
                }
                return;
            }
            this.mRunnableAt = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            this.mRunnableAtReason = 0;
        }
    }

    void updateDeferredStates(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue.BroadcastConsumer broadcastConsumer, @android.annotation.NonNull com.android.server.am.BroadcastProcessQueue.BroadcastConsumer broadcastConsumer2) {
        boolean shouldBeDeferred = shouldBeDeferred();
        if (this.mLastDeferredStates != shouldBeDeferred) {
            this.mLastDeferredStates = shouldBeDeferred;
            if (shouldBeDeferred) {
                forEachMatchingBroadcast(new com.android.server.am.BroadcastProcessQueue.BroadcastPredicate() { // from class: com.android.server.am.BroadcastProcessQueue$$ExternalSyntheticLambda1
                    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
                    public final boolean test(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
                        boolean lambda$updateDeferredStates$0;
                        lambda$updateDeferredStates$0 = com.android.server.am.BroadcastProcessQueue.lambda$updateDeferredStates$0(broadcastRecord, i);
                        return lambda$updateDeferredStates$0;
                    }
                }, broadcastConsumer, false);
            } else {
                forEachMatchingBroadcast(new com.android.server.am.BroadcastProcessQueue.BroadcastPredicate() { // from class: com.android.server.am.BroadcastProcessQueue$$ExternalSyntheticLambda2
                    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
                    public final boolean test(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
                        boolean lambda$updateDeferredStates$1;
                        lambda$updateDeferredStates$1 = com.android.server.am.BroadcastProcessQueue.lambda$updateDeferredStates$1(broadcastRecord, i);
                        return lambda$updateDeferredStates$1;
                    }
                }, broadcastConsumer2, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateDeferredStates$0(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        return broadcastRecord.getDeliveryState(i) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateDeferredStates$1(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        return broadcastRecord.getDeliveryState(i) == 6;
    }

    void clearDeferredStates(@android.annotation.NonNull com.android.server.am.BroadcastProcessQueue.BroadcastConsumer broadcastConsumer) {
        if (this.mLastDeferredStates) {
            this.mLastDeferredStates = false;
            forEachMatchingBroadcast(new com.android.server.am.BroadcastProcessQueue.BroadcastPredicate() { // from class: com.android.server.am.BroadcastProcessQueue$$ExternalSyntheticLambda0
                @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
                public final boolean test(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
                    boolean lambda$clearDeferredStates$2;
                    lambda$clearDeferredStates$2 = com.android.server.am.BroadcastProcessQueue.lambda$clearDeferredStates$2(broadcastRecord, i);
                    return lambda$clearDeferredStates$2;
                }
            }, broadcastConsumer, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$clearDeferredStates$2(com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        return broadcastRecord.getDeliveryState(i) == 6;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldBeDeferred() {
        if (this.mRunnableAtInvalidated) {
            updateRunnableAt();
        }
        return this.mRunnableAtReason == 1 || this.mRunnableAtReason == 8;
    }

    public void assertHealthLocked() {
        if (!isActive()) {
            com.android.internal.util.Preconditions.checkState(!this.mRunnableAtInvalidated, "mRunnableAtInvalidated");
        }
        assertHealthLocked(this.mPending);
        assertHealthLocked(this.mPendingUrgent);
        assertHealthLocked(this.mPendingOffload);
    }

    private void assertHealthLocked(@android.annotation.NonNull java.util.ArrayDeque<com.android.internal.os.SomeArgs> arrayDeque) {
        if (arrayDeque.isEmpty()) {
            return;
        }
        java.util.Iterator<com.android.internal.os.SomeArgs> descendingIterator = arrayDeque.descendingIterator();
        while (descendingIterator.hasNext()) {
            com.android.internal.os.SomeArgs next = descendingIterator.next();
            com.android.server.am.BroadcastRecord broadcastRecord = (com.android.server.am.BroadcastRecord) next.arg1;
            if (!com.android.server.am.BroadcastRecord.isDeliveryStateTerminal(broadcastRecord.getDeliveryState(next.argi1)) && !broadcastRecord.isDeferUntilActive()) {
                com.android.internal.util.Preconditions.checkState(android.os.SystemClock.uptimeMillis() - broadcastRecord.enqueueTime < 600000, "waitingTime");
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static com.android.server.am.BroadcastProcessQueue insertIntoRunnableList(@android.annotation.Nullable com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, @android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue2) {
        if (broadcastProcessQueue == null) {
            return broadcastProcessQueue2;
        }
        long runnableAt = broadcastProcessQueue2.getRunnableAt();
        com.android.server.am.BroadcastProcessQueue broadcastProcessQueue3 = null;
        com.android.server.am.BroadcastProcessQueue broadcastProcessQueue4 = broadcastProcessQueue;
        while (broadcastProcessQueue4 != null) {
            if (broadcastProcessQueue4.getRunnableAt() > runnableAt) {
                broadcastProcessQueue2.runnableAtNext = broadcastProcessQueue4;
                broadcastProcessQueue2.runnableAtPrev = broadcastProcessQueue4.runnableAtPrev;
                if (broadcastProcessQueue2.runnableAtNext != null) {
                    broadcastProcessQueue2.runnableAtNext.runnableAtPrev = broadcastProcessQueue2;
                }
                if (broadcastProcessQueue2.runnableAtPrev != null) {
                    broadcastProcessQueue2.runnableAtPrev.runnableAtNext = broadcastProcessQueue2;
                }
                return broadcastProcessQueue4 == broadcastProcessQueue ? broadcastProcessQueue2 : broadcastProcessQueue;
            }
            broadcastProcessQueue3 = broadcastProcessQueue4;
            broadcastProcessQueue4 = broadcastProcessQueue4.runnableAtNext;
        }
        broadcastProcessQueue2.runnableAtPrev = broadcastProcessQueue3;
        broadcastProcessQueue2.runnableAtPrev.runnableAtNext = broadcastProcessQueue2;
        return broadcastProcessQueue;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static com.android.server.am.BroadcastProcessQueue removeFromRunnableList(@android.annotation.Nullable com.android.server.am.BroadcastProcessQueue broadcastProcessQueue, @android.annotation.NonNull com.android.server.am.BroadcastProcessQueue broadcastProcessQueue2) {
        if (broadcastProcessQueue == broadcastProcessQueue2) {
            broadcastProcessQueue = broadcastProcessQueue2.runnableAtNext;
        }
        if (broadcastProcessQueue2.runnableAtNext != null) {
            broadcastProcessQueue2.runnableAtNext.runnableAtPrev = broadcastProcessQueue2.runnableAtPrev;
        }
        if (broadcastProcessQueue2.runnableAtPrev != null) {
            broadcastProcessQueue2.runnableAtPrev.runnableAtNext = broadcastProcessQueue2.runnableAtNext;
        }
        broadcastProcessQueue2.runnableAtNext = null;
        broadcastProcessQueue2.runnableAtPrev = null;
        return broadcastProcessQueue;
    }

    void setTimeoutScheduled(boolean z) {
        this.mTimeoutScheduled = z;
    }

    boolean timeoutScheduled() {
        return this.mTimeoutScheduled;
    }

    public java.lang.String toString() {
        if (this.mCachedToString == null) {
            this.mCachedToString = "BroadcastProcessQueue{" + toShortString() + "}";
        }
        return this.mCachedToString;
    }

    public java.lang.String toShortString() {
        if (this.mCachedToShortString == null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(" ");
            sb.append(this.app != null ? java.lang.Integer.valueOf(this.app.getPid()) : "?");
            sb.append(":");
            sb.append(this.processName);
            sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            sb.append(android.os.UserHandle.formatUid(this.uid));
            this.mCachedToShortString = sb.toString();
        }
        return this.mCachedToShortString;
    }

    public java.lang.String describeStateLocked() {
        return describeStateLocked(android.os.SystemClock.uptimeMillis());
    }

    public java.lang.String describeStateLocked(long j) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (isRunnable()) {
            sb.append("runnable at ");
            android.util.TimeUtils.formatDuration(getRunnableAt(), j, sb);
        } else {
            sb.append("not runnable");
        }
        sb.append(" because ");
        sb.append(reasonToString(this.mRunnableAtReason));
        return sb.toString();
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dumpLocked(long j, @android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter) {
        if (this.mActive == null && isEmpty()) {
            return;
        }
        indentingPrintWriter.print(toShortString());
        indentingPrintWriter.print(" ");
        indentingPrintWriter.print(describeStateLocked(j));
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        dumpProcessState(indentingPrintWriter);
        dumpBroadcastCounts(indentingPrintWriter);
        if (this.mActive != null) {
            dumpRecord("ACTIVE", j, indentingPrintWriter, this.mActive, this.mActiveIndex);
        }
        java.util.Iterator<com.android.internal.os.SomeArgs> it = this.mPendingUrgent.iterator();
        while (it.hasNext()) {
            com.android.internal.os.SomeArgs next = it.next();
            dumpRecord("URGENT", j, indentingPrintWriter, (com.android.server.am.BroadcastRecord) next.arg1, next.argi1);
        }
        java.util.Iterator<com.android.internal.os.SomeArgs> it2 = this.mPending.iterator();
        while (it2.hasNext()) {
            com.android.internal.os.SomeArgs next2 = it2.next();
            dumpRecord(null, j, indentingPrintWriter, (com.android.server.am.BroadcastRecord) next2.arg1, next2.argi1);
        }
        java.util.Iterator<com.android.internal.os.SomeArgs> it3 = this.mPendingOffload.iterator();
        while (it3.hasNext()) {
            com.android.internal.os.SomeArgs next3 = it3.next();
            dumpRecord("OFFLOAD", j, indentingPrintWriter, (com.android.server.am.BroadcastRecord) next3.arg1, next3.argi1);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    @dalvik.annotation.optimization.NeverCompile
    private void dumpProcessState(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (this.mUidForeground) {
            sb.append("FG");
        }
        if (this.mProcessFreezable) {
            if (sb.length() > 0) {
                sb.append("|");
            }
            sb.append("FRZ");
        }
        if (this.mProcessInstrumented) {
            if (sb.length() > 0) {
                sb.append("|");
            }
            sb.append("INSTR");
        }
        if (this.mProcessPersistent) {
            if (sb.length() > 0) {
                sb.append("|");
            }
            sb.append("PER");
        }
        if (sb.length() > 0) {
            indentingPrintWriter.print("state:");
            indentingPrintWriter.println(sb);
        }
        if (this.runningOomAdjusted) {
            indentingPrintWriter.print("runningOomAdjusted:");
            indentingPrintWriter.println(this.runningOomAdjusted);
        }
        if (this.mActiveReEnqueued) {
            indentingPrintWriter.print("activeReEnqueued:");
            indentingPrintWriter.println(this.mActiveReEnqueued);
        }
    }

    @dalvik.annotation.optimization.NeverCompile
    private void dumpBroadcastCounts(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("e:");
        indentingPrintWriter.print(this.mCountEnqueued);
        indentingPrintWriter.print(" d:");
        indentingPrintWriter.print(this.mCountDeferred);
        indentingPrintWriter.print(" f:");
        indentingPrintWriter.print(this.mCountForeground);
        indentingPrintWriter.print(" fd:");
        indentingPrintWriter.print(this.mCountForegroundDeferred);
        indentingPrintWriter.print(" o:");
        indentingPrintWriter.print(this.mCountOrdered);
        indentingPrintWriter.print(" a:");
        indentingPrintWriter.print(this.mCountAlarm);
        indentingPrintWriter.print(" p:");
        indentingPrintWriter.print(this.mCountPrioritized);
        indentingPrintWriter.print(" pd:");
        indentingPrintWriter.print(this.mCountPrioritizedDeferred);
        indentingPrintWriter.print(" int:");
        indentingPrintWriter.print(this.mCountInteractive);
        indentingPrintWriter.print(" rt:");
        indentingPrintWriter.print(this.mCountResultTo);
        indentingPrintWriter.print(" ins:");
        indentingPrintWriter.print(this.mCountInstrumented);
        indentingPrintWriter.print(" m:");
        indentingPrintWriter.print(this.mCountManifest);
        indentingPrintWriter.print(" csi:");
        indentingPrintWriter.print(this.mActiveCountSinceIdle);
        indentingPrintWriter.print(" adcsi:");
        indentingPrintWriter.print(this.mActiveAssumedDeliveryCountSinceIdle);
        indentingPrintWriter.print(" ccu:");
        indentingPrintWriter.print(this.mActiveCountConsecutiveUrgent);
        indentingPrintWriter.print(" ccn:");
        indentingPrintWriter.print(this.mActiveCountConsecutiveNormal);
        indentingPrintWriter.println();
    }

    @dalvik.annotation.optimization.NeverCompile
    private void dumpRecord(@android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord, int i) {
        android.util.TimeUtils.formatDuration(broadcastRecord.enqueueTime, j, indentingPrintWriter);
        indentingPrintWriter.print(' ');
        indentingPrintWriter.println(broadcastRecord.toShortString());
        indentingPrintWriter.print("    ");
        int i2 = broadcastRecord.delivery[i];
        indentingPrintWriter.print(com.android.server.am.BroadcastRecord.deliveryStateToString(i2));
        if (i2 == 4) {
            indentingPrintWriter.print(" at ");
            android.util.TimeUtils.formatDuration(broadcastRecord.scheduledTime[i], j, indentingPrintWriter);
        }
        if (str != null) {
            indentingPrintWriter.print(' ');
            indentingPrintWriter.print(str);
        }
        java.lang.Object obj = broadcastRecord.receivers.get(i);
        if (obj instanceof com.android.server.am.BroadcastFilter) {
            indentingPrintWriter.print(" for registered ");
            indentingPrintWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode((com.android.server.am.BroadcastFilter) obj)));
        } else {
            indentingPrintWriter.print(" for manifest ");
            indentingPrintWriter.print(((android.content.pm.ResolveInfo) obj).activityInfo.name);
        }
        indentingPrintWriter.println();
        int i3 = broadcastRecord.blockedUntilBeyondCount[i];
        if (i3 != -1) {
            indentingPrintWriter.print("    blocked until ");
            indentingPrintWriter.print(i3);
            indentingPrintWriter.print(", currently at ");
            indentingPrintWriter.print(broadcastRecord.beyondCount);
            indentingPrintWriter.print(" of ");
            indentingPrintWriter.println(broadcastRecord.receivers.size());
        }
    }
}
