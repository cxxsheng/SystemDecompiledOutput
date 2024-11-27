package com.android.server.tare;

/* loaded from: classes2.dex */
class Agent {
    private static final java.lang.String ALARM_TAG_AFFORDABILITY_CHECK = "*tare.affordability_check*";
    private static final boolean DEBUG;
    private static final int MSG_CHECK_ALL_AFFORDABILITY = 0;
    private static final int MSG_CHECK_INDIVIDUAL_AFFORDABILITY = 1;
    private static final java.lang.String TAG = "TARE-" + com.android.server.tare.Agent.class.getSimpleName();
    private final com.android.server.tare.Analyst mAnalyst;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.tare.Agent.BalanceThresholdAlarmQueue mBalanceThresholdAlarmQueue;
    private final com.android.server.tare.InternalResourceService mIrs;
    private final java.lang.Object mLock;
    private final com.android.server.tare.Scribe mScribe;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, android.util.SparseArrayMap<java.lang.String, com.android.server.tare.Agent.OngoingEvent>> mCurrentOngoingEvents = new android.util.SparseArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<java.lang.String, android.util.ArraySet<com.android.server.tare.Agent.ActionAffordabilityNote>> mActionAffordabilityNotes = new android.util.SparseArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.tare.Agent.TotalDeltaCalculator mTotalDeltaCalculator = new com.android.server.tare.Agent.TotalDeltaCalculator();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.tare.Agent.TrendCalculator mTrendCalculator = new com.android.server.tare.Agent.TrendCalculator();
    private final com.android.server.tare.Agent.OngoingEventUpdater mOngoingEventUpdater = new com.android.server.tare.Agent.OngoingEventUpdater();
    private final com.android.server.tare.Agent.AgentHandler mHandler = new com.android.server.tare.Agent.AgentHandler(com.android.server.tare.TareHandlerThread.get().getLooper());
    private final com.android.server.usage.AppStandbyInternal mAppStandbyInternal = (com.android.server.usage.AppStandbyInternal) com.android.server.LocalServices.getService(com.android.server.usage.AppStandbyInternal.class);

    static {
        DEBUG = com.android.server.tare.InternalResourceService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    Agent(@android.annotation.NonNull com.android.server.tare.InternalResourceService internalResourceService, @android.annotation.NonNull com.android.server.tare.Scribe scribe, @android.annotation.NonNull com.android.server.tare.Analyst analyst) {
        this.mLock = internalResourceService.getLock();
        this.mIrs = internalResourceService;
        this.mScribe = scribe;
        this.mAnalyst = analyst;
        this.mBalanceThresholdAlarmQueue = new com.android.server.tare.Agent.BalanceThresholdAlarmQueue(this.mIrs.getContext(), com.android.server.tare.TareHandlerThread.get().getLooper());
    }

    private class TotalDeltaCalculator implements java.util.function.Consumer<com.android.server.tare.Agent.OngoingEvent> {
        private com.android.server.tare.Ledger mLedger;
        private long mNow;
        private long mNowElapsed;
        private long mTotal;

        private TotalDeltaCalculator() {
        }

        void reset(@android.annotation.NonNull com.android.server.tare.Ledger ledger, long j, long j2) {
            this.mLedger = ledger;
            this.mNowElapsed = j;
            this.mNow = j2;
            this.mTotal = 0L;
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.tare.Agent.OngoingEvent ongoingEvent) {
            this.mTotal += com.android.server.tare.Agent.this.getActualDeltaLocked(ongoingEvent, this.mLedger, this.mNowElapsed, this.mNow).price;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long getBalanceLocked(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.tare.Ledger ledgerLocked = this.mScribe.getLedgerLocked(i, str);
        long currentBalance = ledgerLocked.getCurrentBalance();
        android.util.SparseArrayMap sparseArrayMap = (android.util.SparseArrayMap) this.mCurrentOngoingEvents.get(i, str);
        if (sparseArrayMap != null) {
            this.mTotalDeltaCalculator.reset(ledgerLocked, android.os.SystemClock.elapsedRealtime(), com.android.server.tare.TareUtils.getCurrentTimeMillis());
            sparseArrayMap.forEach(this.mTotalDeltaCalculator);
            return currentBalance + this.mTotalDeltaCalculator.mTotal;
        }
        return currentBalance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isAffordableLocked(long j, long j2, long j3) {
        return j >= j2 && this.mScribe.getRemainingConsumableCakesLocked() >= j3;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void noteInstantaneousEventLocked(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2) {
        if (this.mIrs.isSystem(i, str)) {
            return;
        }
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        com.android.server.tare.Ledger ledgerLocked = this.mScribe.getLedgerLocked(i, str);
        com.android.server.tare.CompleteEconomicPolicy completeEconomicPolicyLocked = this.mIrs.getCompleteEconomicPolicyLocked();
        int eventType = com.android.server.tare.EconomicPolicy.getEventType(i2);
        switch (eventType) {
            case Integer.MIN_VALUE:
                com.android.server.tare.EconomicPolicy.Reward reward = completeEconomicPolicyLocked.getReward(i2);
                if (reward != null) {
                    recordTransactionLocked(i, str, ledgerLocked, new com.android.server.tare.Ledger.Transaction(currentTimeMillis, currentTimeMillis, i2, str2, java.lang.Math.max(0L, java.lang.Math.min(reward.maxDailyReward - ledgerLocked.get24HourSum(i2, currentTimeMillis), reward.instantReward)), 0L), true);
                    break;
                }
                break;
            case 1073741824:
                com.android.server.tare.EconomicPolicy.Cost costOfAction = completeEconomicPolicyLocked.getCostOfAction(i2, i, str);
                recordTransactionLocked(i, str, ledgerLocked, new com.android.server.tare.Ledger.Transaction(currentTimeMillis, currentTimeMillis, i2, str2, -costOfAction.price, costOfAction.costToProduce), true);
                break;
            default:
                android.util.Slog.w(TAG, "Unsupported event type: " + eventType);
                break;
        }
        scheduleBalanceCheckLocked(i, str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void noteOngoingEventLocked(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, long j) {
        noteOngoingEventLocked(i, str, i2, str2, j, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void noteOngoingEventLocked(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, long j, boolean z) {
        if (this.mIrs.isSystem(i, str)) {
            return;
        }
        android.util.SparseArrayMap sparseArrayMap = (android.util.SparseArrayMap) this.mCurrentOngoingEvents.get(i, str);
        if (sparseArrayMap == null) {
            sparseArrayMap = new android.util.SparseArrayMap();
            this.mCurrentOngoingEvents.add(i, str, sparseArrayMap);
        }
        com.android.server.tare.Agent.OngoingEvent ongoingEvent = (com.android.server.tare.Agent.OngoingEvent) sparseArrayMap.get(i2, str2);
        com.android.server.tare.CompleteEconomicPolicy completeEconomicPolicyLocked = this.mIrs.getCompleteEconomicPolicyLocked();
        int eventType = com.android.server.tare.EconomicPolicy.getEventType(i2);
        switch (eventType) {
            case Integer.MIN_VALUE:
                com.android.server.tare.EconomicPolicy.Reward reward = completeEconomicPolicyLocked.getReward(i2);
                if (reward != null) {
                    if (ongoingEvent == null) {
                        sparseArrayMap.add(i2, str2, new com.android.server.tare.Agent.OngoingEvent(i2, str2, j, reward));
                        break;
                    } else {
                        ongoingEvent.refCount++;
                        break;
                    }
                }
                break;
            case 1073741824:
                com.android.server.tare.EconomicPolicy.Cost costOfAction = completeEconomicPolicyLocked.getCostOfAction(i2, i, str);
                if (ongoingEvent == null) {
                    sparseArrayMap.add(i2, str2, new com.android.server.tare.Agent.OngoingEvent(i2, str2, j, costOfAction));
                    break;
                } else {
                    ongoingEvent.refCount++;
                    break;
                }
            default:
                android.util.Slog.w(TAG, "Unsupported event type: " + eventType);
                break;
        }
        if (z) {
            scheduleBalanceCheckLocked(i, str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onDeviceStateChangedLocked() {
        onPricingChangedLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onPricingChangedLocked() {
        onAnythingChangedLocked(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x009e A[SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void onAppStatesChangedLocked(int i, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
        long j;
        int i2;
        com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote;
        boolean z;
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        com.android.server.tare.CompleteEconomicPolicy completeEconomicPolicyLocked = this.mIrs.getCompleteEconomicPolicyLocked();
        int i3 = 0;
        while (i3 < arraySet.size()) {
            java.lang.String valueAt = arraySet.valueAt(i3);
            boolean isVip = this.mIrs.isVip(i, valueAt, elapsedRealtime);
            android.util.SparseArrayMap sparseArrayMap = (android.util.SparseArrayMap) this.mCurrentOngoingEvents.get(i, valueAt);
            if (sparseArrayMap == null) {
                j = currentTimeMillis;
            } else {
                j = currentTimeMillis;
                this.mOngoingEventUpdater.reset(i, valueAt, currentTimeMillis, elapsedRealtime);
                sparseArrayMap.forEach(this.mOngoingEventUpdater);
                android.util.ArraySet arraySet2 = (android.util.ArraySet) this.mActionAffordabilityNotes.get(i, valueAt);
                if (arraySet2 != null) {
                    int size = arraySet2.size();
                    long currentBalance = this.mScribe.getLedgerLocked(i, valueAt).getCurrentBalance();
                    for (int i4 = 0; i4 < size; i4 = i2 + 1) {
                        com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote2 = (com.android.server.tare.Agent.ActionAffordabilityNote) arraySet2.valueAt(i4);
                        actionAffordabilityNote2.recalculateCosts(completeEconomicPolicyLocked, i, valueAt);
                        if (isVip) {
                            i2 = i4;
                            actionAffordabilityNote = actionAffordabilityNote2;
                        } else {
                            i2 = i4;
                            actionAffordabilityNote = actionAffordabilityNote2;
                            if (!isAffordableLocked(currentBalance, actionAffordabilityNote2.getCachedModifiedPrice(), actionAffordabilityNote2.getStockLimitHonoringCtp())) {
                                z = false;
                                if (actionAffordabilityNote.isCurrentlyAffordable() == z) {
                                    com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote3 = actionAffordabilityNote;
                                    actionAffordabilityNote3.setNewAffordability(z);
                                    this.mIrs.postAffordabilityChanged(i, valueAt, actionAffordabilityNote3);
                                }
                            }
                        }
                        z = true;
                        if (actionAffordabilityNote.isCurrentlyAffordable() == z) {
                        }
                    }
                }
                scheduleBalanceCheckLocked(i, valueAt);
            }
            i3++;
            currentTimeMillis = j;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x008e A[SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void onVipStatusChangedLocked(int i, @android.annotation.NonNull java.lang.String str) {
        int i2;
        com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote;
        boolean z;
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        com.android.server.tare.CompleteEconomicPolicy completeEconomicPolicyLocked = this.mIrs.getCompleteEconomicPolicyLocked();
        boolean isVip = this.mIrs.isVip(i, str, elapsedRealtime);
        android.util.SparseArrayMap sparseArrayMap = (android.util.SparseArrayMap) this.mCurrentOngoingEvents.get(i, str);
        if (sparseArrayMap != null) {
            this.mOngoingEventUpdater.reset(i, str, currentTimeMillis, elapsedRealtime);
            sparseArrayMap.forEach(this.mOngoingEventUpdater);
        }
        android.util.ArraySet arraySet = (android.util.ArraySet) this.mActionAffordabilityNotes.get(i, str);
        if (arraySet != null) {
            int size = arraySet.size();
            long currentBalance = this.mScribe.getLedgerLocked(i, str).getCurrentBalance();
            for (int i3 = 0; i3 < size; i3 = i2 + 1) {
                com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote2 = (com.android.server.tare.Agent.ActionAffordabilityNote) arraySet.valueAt(i3);
                actionAffordabilityNote2.recalculateCosts(completeEconomicPolicyLocked, i, str);
                if (isVip) {
                    i2 = i3;
                    actionAffordabilityNote = actionAffordabilityNote2;
                } else {
                    i2 = i3;
                    actionAffordabilityNote = actionAffordabilityNote2;
                    if (!isAffordableLocked(currentBalance, actionAffordabilityNote2.getCachedModifiedPrice(), actionAffordabilityNote2.getStockLimitHonoringCtp())) {
                        z = false;
                        if (actionAffordabilityNote.isCurrentlyAffordable() == z) {
                            com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote3 = actionAffordabilityNote;
                            actionAffordabilityNote3.setNewAffordability(z);
                            this.mIrs.postAffordabilityChanged(i, str, actionAffordabilityNote3);
                        }
                    }
                }
                z = true;
                if (actionAffordabilityNote.isCurrentlyAffordable() == z) {
                }
            }
        }
        scheduleBalanceCheckLocked(i, str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onVipStatusChangedLocked(@android.annotation.NonNull android.util.SparseSetArray<java.lang.String> sparseSetArray) {
        for (int size = sparseSetArray.size() - 1; size >= 0; size--) {
            int keyAt = sparseSetArray.keyAt(size);
            for (int sizeAt = sparseSetArray.sizeAt(size) - 1; sizeAt >= 0; sizeAt--) {
                onVipStatusChangedLocked(keyAt, (java.lang.String) sparseSetArray.valueAt(size, sizeAt));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f9 A[SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onAnythingChangedLocked(boolean z) {
        int i;
        com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote;
        android.util.ArraySet arraySet;
        int i2;
        boolean z2;
        int i3;
        long j;
        java.lang.String str;
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        com.android.server.tare.CompleteEconomicPolicy completeEconomicPolicyLocked = this.mIrs.getCompleteEconomicPolicyLocked();
        int i4 = 1;
        int numMaps = this.mCurrentOngoingEvents.numMaps() - 1;
        while (numMaps >= 0) {
            int keyAt = this.mCurrentOngoingEvents.keyAt(numMaps);
            int numElementsForKey = this.mCurrentOngoingEvents.numElementsForKey(keyAt) - i4;
            while (numElementsForKey >= 0) {
                java.lang.String str2 = (java.lang.String) this.mCurrentOngoingEvents.keyAt(numMaps, numElementsForKey);
                android.util.SparseArrayMap sparseArrayMap = (android.util.SparseArrayMap) this.mCurrentOngoingEvents.valueAt(numMaps, numElementsForKey);
                if (sparseArrayMap == null) {
                    i3 = numElementsForKey;
                    j = currentTimeMillis;
                } else {
                    if (!z) {
                        i3 = numElementsForKey;
                        j = currentTimeMillis;
                        str = str2;
                    } else {
                        long j2 = currentTimeMillis;
                        i3 = numElementsForKey;
                        j = currentTimeMillis;
                        str = str2;
                        this.mOngoingEventUpdater.reset(keyAt, str2, j2, elapsedRealtime);
                        sparseArrayMap.forEach(this.mOngoingEventUpdater);
                    }
                    scheduleBalanceCheckLocked(keyAt, str);
                }
                numElementsForKey = i3 - 1;
                currentTimeMillis = j;
            }
            numMaps--;
            i4 = 1;
        }
        for (int numMaps2 = this.mActionAffordabilityNotes.numMaps() - 1; numMaps2 >= 0; numMaps2--) {
            int keyAt2 = this.mActionAffordabilityNotes.keyAt(numMaps2);
            for (int numElementsForKey2 = this.mActionAffordabilityNotes.numElementsForKey(keyAt2) - 1; numElementsForKey2 >= 0; numElementsForKey2--) {
                java.lang.String str3 = (java.lang.String) this.mActionAffordabilityNotes.keyAt(numMaps2, numElementsForKey2);
                android.util.ArraySet arraySet2 = (android.util.ArraySet) this.mActionAffordabilityNotes.valueAt(numMaps2, numElementsForKey2);
                if (arraySet2 != null) {
                    long balanceLocked = getBalanceLocked(keyAt2, str3);
                    boolean isVip = this.mIrs.isVip(keyAt2, str3, elapsedRealtime);
                    int i5 = 0;
                    for (int size = arraySet2.size(); i5 < size; size = i2) {
                        com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote2 = (com.android.server.tare.Agent.ActionAffordabilityNote) arraySet2.valueAt(i5);
                        actionAffordabilityNote2.recalculateCosts(completeEconomicPolicyLocked, keyAt2, str3);
                        if (isVip) {
                            i = i5;
                            actionAffordabilityNote = actionAffordabilityNote2;
                            arraySet = arraySet2;
                            i2 = size;
                        } else {
                            i = i5;
                            actionAffordabilityNote = actionAffordabilityNote2;
                            arraySet = arraySet2;
                            i2 = size;
                            if (!isAffordableLocked(balanceLocked, actionAffordabilityNote2.getCachedModifiedPrice(), actionAffordabilityNote2.getStockLimitHonoringCtp())) {
                                z2 = false;
                                if (actionAffordabilityNote.isCurrentlyAffordable() == z2) {
                                    com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote3 = actionAffordabilityNote;
                                    actionAffordabilityNote3.setNewAffordability(z2);
                                    this.mIrs.postAffordabilityChanged(keyAt2, str3, actionAffordabilityNote3);
                                }
                                i5 = i + 1;
                                arraySet2 = arraySet;
                            }
                        }
                        z2 = true;
                        if (actionAffordabilityNote.isCurrentlyAffordable() == z2) {
                        }
                        i5 = i + 1;
                        arraySet2 = arraySet;
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void stopOngoingActionLocked(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, long j, long j2) {
        stopOngoingActionLocked(i, str, i2, str2, j, j2, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void stopOngoingActionLocked(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, long j, long j2, boolean z, boolean z2) {
        java.lang.String str3;
        if (this.mIrs.isSystem(i, str)) {
            return;
        }
        com.android.server.tare.Ledger ledgerLocked = this.mScribe.getLedgerLocked(i, str);
        android.util.SparseArrayMap sparseArrayMap = (android.util.SparseArrayMap) this.mCurrentOngoingEvents.get(i, str);
        if (sparseArrayMap == null) {
            android.util.Slog.w(TAG, "No ongoing transactions for " + com.android.server.tare.TareUtils.appToString(i, str));
            return;
        }
        com.android.server.tare.Agent.OngoingEvent ongoingEvent = (com.android.server.tare.Agent.OngoingEvent) sparseArrayMap.get(i2, str2);
        if (ongoingEvent == null) {
            java.lang.String str4 = TAG;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Nonexistent ongoing transaction ");
            sb.append(com.android.server.tare.EconomicPolicy.eventToString(i2));
            if (str2 == null) {
                str3 = "";
            } else {
                str3 = ":" + str2;
            }
            sb.append(str3);
            sb.append(" for ");
            sb.append(com.android.server.tare.TareUtils.appToString(i, str));
            sb.append(" ended");
            android.util.Slog.w(str4, sb.toString());
            return;
        }
        ongoingEvent.refCount--;
        if (ongoingEvent.refCount <= 0) {
            long j3 = j2 - (j - ongoingEvent.startTimeElapsed);
            com.android.server.tare.EconomicPolicy.Cost actualDeltaLocked = getActualDeltaLocked(ongoingEvent, ledgerLocked, j, j2);
            recordTransactionLocked(i, str, ledgerLocked, new com.android.server.tare.Ledger.Transaction(j3, j2, i2, str2, actualDeltaLocked.price, actualDeltaLocked.costToProduce), z2);
            sparseArrayMap.delete(i2, str2);
        }
        if (z) {
            scheduleBalanceCheckLocked(i, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tare.EconomicPolicy.Cost getActualDeltaLocked(@android.annotation.NonNull com.android.server.tare.Agent.OngoingEvent ongoingEvent, @android.annotation.NonNull com.android.server.tare.Ledger ledger, long j, long j2) {
        long j3 = (j - ongoingEvent.startTimeElapsed) / 1000;
        long deltaPerSec = ongoingEvent.getDeltaPerSec() * j3;
        if (ongoingEvent.reward == null) {
            return new com.android.server.tare.EconomicPolicy.Cost(j3 * ongoingEvent.getCtpPerSec(), deltaPerSec);
        }
        return new com.android.server.tare.EconomicPolicy.Cost(0L, java.lang.Math.max(0L, java.lang.Math.min(ongoingEvent.reward.maxDailyReward - ledger.get24HourSum(ongoingEvent.eventId, j2), deltaPerSec)));
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x015b A[SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void recordTransactionLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.Ledger ledger, @android.annotation.NonNull com.android.server.tare.Ledger.Transaction transaction, boolean z) {
        com.android.server.tare.Ledger.Transaction transaction2;
        android.util.ArraySet arraySet;
        int i2;
        com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote;
        boolean z2;
        com.android.server.tare.Ledger.Transaction transaction3 = transaction;
        if (!DEBUG && transaction3.delta == 0) {
            return;
        }
        if (this.mIrs.isSystem(i, str)) {
            android.util.Slog.wtfStack(TAG, "Tried to adjust system balance for " + com.android.server.tare.TareUtils.appToString(i, str));
            return;
        }
        boolean isVip = this.mIrs.isVip(i, str);
        if (isVip) {
            transaction3 = new com.android.server.tare.Ledger.Transaction(transaction3.startTimeMs, transaction3.endTimeMs, transaction3.eventId, transaction3.tag, 0L, transaction3.ctp);
        }
        com.android.server.tare.CompleteEconomicPolicy completeEconomicPolicyLocked = this.mIrs.getCompleteEconomicPolicyLocked();
        long currentBalance = ledger.getCurrentBalance();
        long maxSatiatedBalance = completeEconomicPolicyLocked.getMaxSatiatedBalance(i, str);
        if (transaction3.delta > 0 && transaction3.delta + currentBalance > maxSatiatedBalance) {
            long max = java.lang.Math.max(0L, maxSatiatedBalance - currentBalance);
            java.lang.String str2 = TAG;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Would result in becoming too rich. Decreasing transaction ");
            sb.append(com.android.server.tare.EconomicPolicy.eventToString(transaction3.eventId));
            sb.append(transaction3.tag == null ? "" : ":" + transaction3.tag);
            sb.append(" for ");
            sb.append(com.android.server.tare.TareUtils.appToString(i, str));
            sb.append(" by ");
            sb.append(com.android.server.tare.TareUtils.cakeToString(transaction3.delta - max));
            android.util.Slog.i(str2, sb.toString());
            transaction2 = new com.android.server.tare.Ledger.Transaction(transaction3.startTimeMs, transaction3.endTimeMs, transaction3.eventId, transaction3.tag, max, transaction3.ctp);
        } else {
            transaction2 = transaction3;
        }
        ledger.recordTransaction(transaction2);
        this.mScribe.adjustRemainingConsumableCakesLocked(-transaction2.ctp);
        this.mAnalyst.noteTransaction(transaction2);
        if (transaction2.delta != 0 && z && (arraySet = (android.util.ArraySet) this.mActionAffordabilityNotes.get(i, str)) != null) {
            long currentBalance2 = ledger.getCurrentBalance();
            for (int i3 = 0; i3 < arraySet.size(); i3 = i2 + 1) {
                com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote2 = (com.android.server.tare.Agent.ActionAffordabilityNote) arraySet.valueAt(i3);
                if (isVip) {
                    i2 = i3;
                    actionAffordabilityNote = actionAffordabilityNote2;
                } else {
                    i2 = i3;
                    actionAffordabilityNote = actionAffordabilityNote2;
                    if (!isAffordableLocked(currentBalance2, actionAffordabilityNote2.getCachedModifiedPrice(), actionAffordabilityNote2.getStockLimitHonoringCtp())) {
                        z2 = false;
                        if (actionAffordabilityNote.isCurrentlyAffordable() == z2) {
                            com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote3 = actionAffordabilityNote;
                            actionAffordabilityNote3.setNewAffordability(z2);
                            this.mIrs.postAffordabilityChanged(i, str, actionAffordabilityNote3);
                        }
                    }
                }
                z2 = true;
                if (actionAffordabilityNote.isCurrentlyAffordable() == z2) {
                }
            }
        }
        if (transaction2.ctp != 0) {
            this.mHandler.sendEmptyMessage(0);
            this.mIrs.maybePerformQuantitativeEasingLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void reclaimAllAssetsLocked(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        com.android.server.tare.Ledger ledgerLocked = this.mScribe.getLedgerLocked(i, str);
        long currentBalance = ledgerLocked.getCurrentBalance();
        if (currentBalance <= 0) {
            return;
        }
        if (DEBUG) {
            android.util.Slog.i(TAG, "Reclaiming " + com.android.server.tare.TareUtils.cakeToString(currentBalance) + " from " + com.android.server.tare.TareUtils.appToString(i, str) + " because of " + com.android.server.tare.EconomicPolicy.eventToString(i2));
        }
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        recordTransactionLocked(i, str, ledgerLocked, new com.android.server.tare.Ledger.Transaction(currentTimeMillis, currentTimeMillis, i2, null, -currentBalance, 0L), true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void reclaimUnusedAssetsLocked(double d, long j, boolean z) {
        int i;
        int i2;
        int i3;
        long minBalanceLocked;
        com.android.server.tare.CompleteEconomicPolicy completeEconomicPolicyLocked = this.mIrs.getCompleteEconomicPolicyLocked();
        android.util.SparseArrayMap<java.lang.String, com.android.server.tare.Ledger> ledgersLocked = this.mScribe.getLedgersLocked();
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        int i4 = 0;
        while (i4 < ledgersLocked.numMaps()) {
            int keyAt = ledgersLocked.keyAt(i4);
            int i5 = 0;
            while (i5 < ledgersLocked.numElementsForKey(keyAt)) {
                com.android.server.tare.Ledger ledger = (com.android.server.tare.Ledger) ledgersLocked.valueAt(i4, i5);
                long currentBalance = ledger.getCurrentBalance();
                if (currentBalance <= 0) {
                    i = i5;
                    i2 = keyAt;
                    i3 = i4;
                } else {
                    java.lang.String str = (java.lang.String) ledgersLocked.keyAt(i4, i5);
                    if (this.mAppStandbyInternal.getTimeSinceLastUsedByUser(str, keyAt) < j) {
                        i = i5;
                        i2 = keyAt;
                        i3 = i4;
                    } else {
                        if (!z) {
                            minBalanceLocked = completeEconomicPolicyLocked.getMinSatiatedBalance(keyAt, str);
                        } else {
                            minBalanceLocked = this.mIrs.getMinBalanceLocked(keyAt, str);
                        }
                        long j2 = (long) (currentBalance * d);
                        if (currentBalance - j2 < minBalanceLocked) {
                            j2 = currentBalance - minBalanceLocked;
                        }
                        if (j2 <= 0) {
                            i = i5;
                            i2 = keyAt;
                            i3 = i4;
                        } else {
                            if (DEBUG) {
                                android.util.Slog.i(TAG, "Reclaiming unused wealth! Taking " + com.android.server.tare.TareUtils.cakeToString(j2) + " from " + com.android.server.tare.TareUtils.appToString(keyAt, str));
                            }
                            i = i5;
                            i2 = keyAt;
                            i3 = i4;
                            recordTransactionLocked(keyAt, str, ledger, new com.android.server.tare.Ledger.Transaction(currentTimeMillis, currentTimeMillis, 2, null, -j2, 0L), true);
                        }
                    }
                }
                i5 = i + 1;
                keyAt = i2;
                i4 = i3;
            }
            i4++;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onAppUnexemptedLocked(int i, @android.annotation.NonNull java.lang.String str) {
        double d;
        if (getBalanceLocked(i, str) <= this.mIrs.getMinBalanceLocked(i, str)) {
            return;
        }
        long timeSinceLastUsedByUser = this.mAppStandbyInternal.getTimeSinceLastUsedByUser(str, i);
        if (timeSinceLastUsedByUser < 86400000) {
            d = 0.25d;
        } else if (timeSinceLastUsedByUser < 172800000) {
            d = 0.5d;
        } else if (timeSinceLastUsedByUser < 259200000) {
            d = 0.75d;
        } else {
            d = 1.0d;
        }
        long j = (long) ((r3 - r5) * d);
        if (j > 0) {
            if (DEBUG) {
                android.util.Slog.i(TAG, "Reclaiming bonus wealth! Taking " + j + " from " + com.android.server.tare.TareUtils.appToString(i, str));
            }
            long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
            recordTransactionLocked(i, str, this.mScribe.getLedgerLocked(i, str), new com.android.server.tare.Ledger.Transaction(currentTimeMillis, currentTimeMillis, 4, null, -j, 0L), true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onAppRestrictedLocked(int i, @android.annotation.NonNull java.lang.String str) {
        reclaimAllAssetsLocked(i, str, 5);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onAppUnrestrictedLocked(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.tare.Ledger ledgerLocked = this.mScribe.getLedgerLocked(i, str);
        if (ledgerLocked.getCurrentBalance() > 0) {
            android.util.Slog.wtf(TAG, "App " + str + " had credits while it was restricted");
            return;
        }
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        recordTransactionLocked(i, str, ledgerLocked, new com.android.server.tare.Ledger.Transaction(currentTimeMillis, currentTimeMillis, 6, null, this.mIrs.getMinBalanceLocked(i, str), 0L), true);
    }

    private boolean shouldGiveCredits(@android.annotation.NonNull com.android.server.tare.InstalledPackageInfo installedPackageInfo) {
        if (!installedPackageInfo.hasCode) {
            return false;
        }
        int userId = android.os.UserHandle.getUserId(installedPackageInfo.uid);
        return (this.mIrs.isSystem(userId, installedPackageInfo.packageName) || this.mIrs.isPackageRestricted(userId, installedPackageInfo.packageName)) ? false : true;
    }

    void onCreditSupplyChanged() {
        this.mHandler.sendEmptyMessage(0);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void distributeBasicIncomeLocked(int i) {
        int i2;
        int i3;
        int i4;
        android.util.SparseArrayMap<java.lang.String, com.android.server.tare.InstalledPackageInfo> installedPackages = this.mIrs.getInstalledPackages();
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        int numMaps = installedPackages.numMaps() - 1;
        while (numMaps >= 0) {
            int keyAt = installedPackages.keyAt(numMaps);
            int numElementsForKeyAt = installedPackages.numElementsForKeyAt(numMaps) - 1;
            while (numElementsForKeyAt >= 0) {
                com.android.server.tare.InstalledPackageInfo installedPackageInfo = (com.android.server.tare.InstalledPackageInfo) installedPackages.valueAt(numMaps, numElementsForKeyAt);
                if (!shouldGiveCredits(installedPackageInfo)) {
                    i2 = numElementsForKeyAt;
                    i3 = keyAt;
                    i4 = numMaps;
                } else {
                    java.lang.String str = installedPackageInfo.packageName;
                    com.android.server.tare.Ledger ledgerLocked = this.mScribe.getLedgerLocked(keyAt, str);
                    double d = i / 100.0d;
                    long minBalanceLocked = this.mIrs.getMinBalanceLocked(keyAt, str) - ledgerLocked.getCurrentBalance();
                    if (minBalanceLocked <= 0) {
                        i2 = numElementsForKeyAt;
                        i3 = keyAt;
                        i4 = numMaps;
                    } else {
                        i2 = numElementsForKeyAt;
                        i3 = keyAt;
                        i4 = numMaps;
                        recordTransactionLocked(keyAt, str, ledgerLocked, new com.android.server.tare.Ledger.Transaction(currentTimeMillis, currentTimeMillis, 0, null, (long) (d * minBalanceLocked), 0L), true);
                    }
                }
                numElementsForKeyAt = i2 - 1;
                keyAt = i3;
                numMaps = i4;
            }
            numMaps--;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void grantBirthrightsLocked() {
        for (int i : ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds()) {
            grantBirthrightsLocked(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void grantBirthrightsLocked(int i) {
        int i2;
        java.util.List<com.android.server.tare.InstalledPackageInfo> installedPackages = this.mIrs.getInstalledPackages(i);
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        int i3 = 0;
        while (i3 < installedPackages.size()) {
            com.android.server.tare.InstalledPackageInfo installedPackageInfo = installedPackages.get(i3);
            if (!shouldGiveCredits(installedPackageInfo)) {
                i2 = i3;
            } else {
                java.lang.String str = installedPackageInfo.packageName;
                com.android.server.tare.Ledger ledgerLocked = this.mScribe.getLedgerLocked(i, str);
                if (ledgerLocked.getCurrentBalance() > 0) {
                    android.util.Slog.wtf(TAG, "App " + str + " had credits before economy was set up");
                    i2 = i3;
                } else {
                    i2 = i3;
                    recordTransactionLocked(i, str, ledgerLocked, new com.android.server.tare.Ledger.Transaction(currentTimeMillis, currentTimeMillis, 1, null, this.mIrs.getMinBalanceLocked(i, str), 0L), true);
                }
            }
            i3 = i2 + 1;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void grantBirthrightLocked(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.tare.Ledger ledgerLocked = this.mScribe.getLedgerLocked(i, str);
        if (ledgerLocked.getCurrentBalance() > 0) {
            android.util.Slog.wtf(TAG, "App " + str + " had credits as soon as it was installed");
            return;
        }
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        recordTransactionLocked(i, str, ledgerLocked, new com.android.server.tare.Ledger.Transaction(currentTimeMillis, currentTimeMillis, 1, null, this.mIrs.getMinBalanceLocked(i, str), 0L), true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onAppExemptedLocked(int i, @android.annotation.NonNull java.lang.String str) {
        long minBalanceLocked = this.mIrs.getMinBalanceLocked(i, str) - getBalanceLocked(i, str);
        if (minBalanceLocked <= 0) {
            return;
        }
        com.android.server.tare.Ledger ledgerLocked = this.mScribe.getLedgerLocked(i, str);
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        recordTransactionLocked(i, str, ledgerLocked, new com.android.server.tare.Ledger.Transaction(currentTimeMillis, currentTimeMillis, 3, null, minBalanceLocked, 0L), true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onPackageRemovedLocked(int i, @android.annotation.NonNull java.lang.String str) {
        this.mScribe.discardLedgerLocked(i, str);
        this.mCurrentOngoingEvents.delete(i, str);
        this.mBalanceThresholdAlarmQueue.removeAlarmForKey(android.content.pm.UserPackage.of(i, str));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onUserRemovedLocked(int i) {
        this.mCurrentOngoingEvents.delete(i);
        this.mBalanceThresholdAlarmQueue.removeAlarmsForUserId(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class TrendCalculator implements java.util.function.Consumer<com.android.server.tare.Agent.OngoingEvent> {
        static final long WILL_NOT_CROSS_THRESHOLD = -1;
        private long mCtpThreshold;
        private long mCurBalance;
        private long mLowerThreshold;
        private long mMaxDeltaPerSecToCtpThreshold;
        private long mMaxDeltaPerSecToLowerThreshold;
        private long mMaxDeltaPerSecToUpperThreshold;
        private long mRemainingConsumableCredits;
        private long mUpperThreshold;

        TrendCalculator() {
        }

        void reset(long j, long j2, @android.annotation.Nullable android.util.ArraySet<com.android.server.tare.Agent.ActionAffordabilityNote> arraySet) {
            this.mCurBalance = j;
            this.mRemainingConsumableCredits = j2;
            this.mMaxDeltaPerSecToLowerThreshold = 0L;
            this.mMaxDeltaPerSecToUpperThreshold = 0L;
            this.mMaxDeltaPerSecToCtpThreshold = 0L;
            this.mUpperThreshold = Long.MIN_VALUE;
            this.mLowerThreshold = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            this.mCtpThreshold = 0L;
            if (arraySet != null) {
                for (int i = 0; i < arraySet.size(); i++) {
                    com.android.server.tare.Agent.ActionAffordabilityNote valueAt = arraySet.valueAt(i);
                    long cachedModifiedPrice = valueAt.getCachedModifiedPrice();
                    if (cachedModifiedPrice <= this.mCurBalance) {
                        if (this.mLowerThreshold != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                            cachedModifiedPrice = java.lang.Math.max(this.mLowerThreshold, cachedModifiedPrice);
                        }
                        this.mLowerThreshold = cachedModifiedPrice;
                    } else {
                        if (this.mUpperThreshold != Long.MIN_VALUE) {
                            cachedModifiedPrice = java.lang.Math.min(this.mUpperThreshold, cachedModifiedPrice);
                        }
                        this.mUpperThreshold = cachedModifiedPrice;
                    }
                    long stockLimitHonoringCtp = valueAt.getStockLimitHonoringCtp();
                    if (stockLimitHonoringCtp <= this.mRemainingConsumableCredits) {
                        this.mCtpThreshold = java.lang.Math.max(this.mCtpThreshold, stockLimitHonoringCtp);
                    }
                }
            }
        }

        long getTimeToCrossLowerThresholdMs() {
            long j;
            if (this.mMaxDeltaPerSecToLowerThreshold == 0 && this.mMaxDeltaPerSecToCtpThreshold == 0) {
                return -1L;
            }
            if (this.mMaxDeltaPerSecToLowerThreshold == 0) {
                j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            } else {
                j = (this.mLowerThreshold - this.mCurBalance) / this.mMaxDeltaPerSecToLowerThreshold;
            }
            if (this.mMaxDeltaPerSecToCtpThreshold != 0) {
                j = java.lang.Math.min(j, (this.mCtpThreshold - this.mRemainingConsumableCredits) / this.mMaxDeltaPerSecToCtpThreshold);
            }
            return j * 1000;
        }

        long getTimeToCrossUpperThresholdMs() {
            if (this.mMaxDeltaPerSecToUpperThreshold == 0) {
                return -1L;
            }
            return ((this.mUpperThreshold - this.mCurBalance) / this.mMaxDeltaPerSecToUpperThreshold) * 1000;
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.tare.Agent.OngoingEvent ongoingEvent) {
            long deltaPerSec = ongoingEvent.getDeltaPerSec();
            if (this.mCurBalance >= this.mLowerThreshold && deltaPerSec < 0) {
                this.mMaxDeltaPerSecToLowerThreshold += deltaPerSec;
            } else if (this.mCurBalance < this.mUpperThreshold && deltaPerSec > 0) {
                this.mMaxDeltaPerSecToUpperThreshold += deltaPerSec;
            }
            long ctpPerSec = ongoingEvent.getCtpPerSec();
            if (this.mRemainingConsumableCredits >= this.mCtpThreshold && deltaPerSec < 0) {
                this.mMaxDeltaPerSecToCtpThreshold -= ctpPerSec;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void scheduleBalanceCheckLocked(int i, @android.annotation.NonNull java.lang.String str) {
        android.util.SparseArrayMap sparseArrayMap = (android.util.SparseArrayMap) this.mCurrentOngoingEvents.get(i, str);
        if (sparseArrayMap == null || this.mIrs.isVip(i, str)) {
            this.mBalanceThresholdAlarmQueue.removeAlarmForKey(android.content.pm.UserPackage.of(i, str));
            return;
        }
        this.mTrendCalculator.reset(getBalanceLocked(i, str), this.mScribe.getRemainingConsumableCakesLocked(), (android.util.ArraySet) this.mActionAffordabilityNotes.get(i, str));
        sparseArrayMap.forEach(this.mTrendCalculator);
        long timeToCrossLowerThresholdMs = this.mTrendCalculator.getTimeToCrossLowerThresholdMs();
        long timeToCrossUpperThresholdMs = this.mTrendCalculator.getTimeToCrossUpperThresholdMs();
        if (timeToCrossLowerThresholdMs != -1) {
            if (timeToCrossUpperThresholdMs != -1) {
                timeToCrossLowerThresholdMs = java.lang.Math.min(timeToCrossLowerThresholdMs, timeToCrossUpperThresholdMs);
            }
            timeToCrossUpperThresholdMs = timeToCrossLowerThresholdMs;
        } else if (timeToCrossUpperThresholdMs == -1) {
            this.mBalanceThresholdAlarmQueue.removeAlarmForKey(android.content.pm.UserPackage.of(i, str));
            return;
        }
        this.mBalanceThresholdAlarmQueue.addAlarm(android.content.pm.UserPackage.of(i, str), android.os.SystemClock.elapsedRealtime() + timeToCrossUpperThresholdMs);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void tearDownLocked() {
        this.mCurrentOngoingEvents.clear();
        this.mBalanceThresholdAlarmQueue.removeAllAlarms();
        this.mHandler.removeAllMessages();
    }

    @com.android.internal.annotations.VisibleForTesting
    static class OngoingEvent {

        @android.annotation.Nullable
        public final com.android.server.tare.EconomicPolicy.Cost actionCost;
        public final int eventId;
        public int refCount;

        @android.annotation.Nullable
        public final com.android.server.tare.EconomicPolicy.Reward reward;
        public final long startTimeElapsed;

        @android.annotation.Nullable
        public final java.lang.String tag;

        OngoingEvent(int i, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull com.android.server.tare.EconomicPolicy.Reward reward) {
            this.startTimeElapsed = j;
            this.eventId = i;
            this.tag = str;
            this.reward = reward;
            this.actionCost = null;
            this.refCount = 1;
        }

        OngoingEvent(int i, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull com.android.server.tare.EconomicPolicy.Cost cost) {
            this.startTimeElapsed = j;
            this.eventId = i;
            this.tag = str;
            this.reward = null;
            this.actionCost = cost;
            this.refCount = 1;
        }

        long getDeltaPerSec() {
            if (this.actionCost != null) {
                return -this.actionCost.price;
            }
            if (this.reward != null) {
                return this.reward.ongoingRewardPerSecond;
            }
            android.util.Slog.wtfStack(com.android.server.tare.Agent.TAG, "No action or reward in ongoing event?!??!");
            return 0L;
        }

        long getCtpPerSec() {
            if (this.actionCost != null) {
                return this.actionCost.costToProduce;
            }
            return 0L;
        }
    }

    private class OngoingEventUpdater implements java.util.function.Consumer<com.android.server.tare.Agent.OngoingEvent> {
        private long mNow;
        private long mNowElapsed;
        private java.lang.String mPkgName;
        private int mUserId;

        private OngoingEventUpdater() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset(int i, java.lang.String str, long j, long j2) {
            this.mUserId = i;
            this.mPkgName = str;
            this.mNow = j;
            this.mNowElapsed = j2;
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.tare.Agent.OngoingEvent ongoingEvent) {
            com.android.server.tare.Agent.this.stopOngoingActionLocked(this.mUserId, this.mPkgName, ongoingEvent.eventId, ongoingEvent.tag, this.mNowElapsed, this.mNow, false, false);
            com.android.server.tare.Agent.this.noteOngoingEventLocked(this.mUserId, this.mPkgName, ongoingEvent.eventId, ongoingEvent.tag, this.mNowElapsed, false);
        }
    }

    private class BalanceThresholdAlarmQueue extends com.android.server.utils.AlarmQueue<android.content.pm.UserPackage> {
        private BalanceThresholdAlarmQueue(android.content.Context context, android.os.Looper looper) {
            super(context, looper, com.android.server.tare.Agent.ALARM_TAG_AFFORDABILITY_CHECK, "Affordability check", true, 15000L);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.utils.AlarmQueue
        public boolean isForUser(@android.annotation.NonNull android.content.pm.UserPackage userPackage, int i) {
            return userPackage.userId == i;
        }

        @Override // com.android.server.utils.AlarmQueue
        protected void processExpiredAlarms(@android.annotation.NonNull android.util.ArraySet<android.content.pm.UserPackage> arraySet) {
            for (int i = 0; i < arraySet.size(); i++) {
                android.content.pm.UserPackage valueAt = arraySet.valueAt(i);
                com.android.server.tare.Agent.this.mHandler.obtainMessage(1, valueAt.userId, 0, valueAt.packageName).sendToTarget();
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void registerAffordabilityChangeListenerLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener affordabilityChangeListener, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
        android.util.ArraySet arraySet = (android.util.ArraySet) this.mActionAffordabilityNotes.get(i, str);
        if (arraySet == null) {
            arraySet = new android.util.ArraySet();
            this.mActionAffordabilityNotes.add(i, str, arraySet);
        }
        com.android.server.tare.CompleteEconomicPolicy completeEconomicPolicyLocked = this.mIrs.getCompleteEconomicPolicyLocked();
        com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote = new com.android.server.tare.Agent.ActionAffordabilityNote(actionBill, affordabilityChangeListener, completeEconomicPolicyLocked);
        if (arraySet.add(actionAffordabilityNote)) {
            boolean z = true;
            if (this.mIrs.getEnabledMode() == 0) {
                actionAffordabilityNote.setNewAffordability(true);
                return;
            }
            boolean isVip = this.mIrs.isVip(i, str);
            actionAffordabilityNote.recalculateCosts(completeEconomicPolicyLocked, i, str);
            if (!isVip && !isAffordableLocked(getBalanceLocked(i, str), actionAffordabilityNote.getCachedModifiedPrice(), actionAffordabilityNote.getStockLimitHonoringCtp())) {
                z = false;
            }
            actionAffordabilityNote.setNewAffordability(z);
            this.mIrs.postAffordabilityChanged(i, str, actionAffordabilityNote);
            scheduleBalanceCheckLocked(i, str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void unregisterAffordabilityChangeListenerLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener affordabilityChangeListener, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill) {
        android.util.ArraySet arraySet = (android.util.ArraySet) this.mActionAffordabilityNotes.get(i, str);
        if (arraySet != null && arraySet.remove(new com.android.server.tare.Agent.ActionAffordabilityNote(actionBill, affordabilityChangeListener, this.mIrs.getCompleteEconomicPolicyLocked()))) {
            scheduleBalanceCheckLocked(i, str);
        }
    }

    static final class ActionAffordabilityNote {
        private final com.android.server.tare.EconomyManagerInternal.ActionBill mActionBill;
        private boolean mIsAffordable;
        private final com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener mListener;
        private long mModifiedPrice;
        private long mStockLimitHonoringCtp;

        @com.android.internal.annotations.VisibleForTesting
        ActionAffordabilityNote(@android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.ActionBill actionBill, @android.annotation.NonNull com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener affordabilityChangeListener, @android.annotation.NonNull com.android.server.tare.EconomicPolicy economicPolicy) {
            this.mActionBill = actionBill;
            java.util.List<com.android.server.tare.EconomyManagerInternal.AnticipatedAction> anticipatedActions = actionBill.getAnticipatedActions();
            for (int i = 0; i < anticipatedActions.size(); i++) {
                com.android.server.tare.EconomyManagerInternal.AnticipatedAction anticipatedAction = anticipatedActions.get(i);
                if (economicPolicy.getAction(anticipatedAction.actionId) == null) {
                    if ((anticipatedAction.actionId & 805306368) == 0) {
                        throw new java.lang.IllegalArgumentException("Invalid action id: " + anticipatedAction.actionId);
                    }
                    android.util.Slog.w(com.android.server.tare.Agent.TAG, "Tracking disabled policy's action? " + anticipatedAction.actionId);
                }
            }
            this.mListener = affordabilityChangeListener;
        }

        @android.annotation.NonNull
        com.android.server.tare.EconomyManagerInternal.ActionBill getActionBill() {
            return this.mActionBill;
        }

        @android.annotation.NonNull
        com.android.server.tare.EconomyManagerInternal.AffordabilityChangeListener getListener() {
            return this.mListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getCachedModifiedPrice() {
            return this.mModifiedPrice;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getStockLimitHonoringCtp() {
            return this.mStockLimitHonoringCtp;
        }

        @com.android.internal.annotations.VisibleForTesting
        void recalculateCosts(@android.annotation.NonNull com.android.server.tare.EconomicPolicy economicPolicy, int i, @android.annotation.NonNull java.lang.String str) {
            com.android.server.tare.EconomicPolicy economicPolicy2 = economicPolicy;
            java.util.List<com.android.server.tare.EconomyManagerInternal.AnticipatedAction> anticipatedActions = this.mActionBill.getAnticipatedActions();
            long j = 0;
            int i2 = 0;
            long j2 = 0;
            while (i2 < anticipatedActions.size()) {
                com.android.server.tare.EconomyManagerInternal.AnticipatedAction anticipatedAction = anticipatedActions.get(i2);
                com.android.server.tare.EconomicPolicy.Action action = economicPolicy2.getAction(anticipatedAction.actionId);
                com.android.server.tare.EconomicPolicy.Cost costOfAction = economicPolicy2.getCostOfAction(anticipatedAction.actionId, i, str);
                java.util.List<com.android.server.tare.EconomyManagerInternal.AnticipatedAction> list = anticipatedActions;
                j += (costOfAction.price * anticipatedAction.numInstantaneousCalls) + (costOfAction.price * (anticipatedAction.ongoingDurationMs / 1000));
                if (action.respectsStockLimit) {
                    j2 += (costOfAction.costToProduce * anticipatedAction.numInstantaneousCalls) + (costOfAction.costToProduce * (anticipatedAction.ongoingDurationMs / 1000));
                }
                i2++;
                economicPolicy2 = economicPolicy;
                anticipatedActions = list;
            }
            this.mModifiedPrice = j;
            this.mStockLimitHonoringCtp = j2;
        }

        boolean isCurrentlyAffordable() {
            return this.mIsAffordable;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNewAffordability(boolean z) {
            this.mIsAffordable = z;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.tare.Agent.ActionAffordabilityNote)) {
                return false;
            }
            com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote = (com.android.server.tare.Agent.ActionAffordabilityNote) obj;
            return this.mActionBill.equals(actionAffordabilityNote.mActionBill) && this.mListener.equals(actionAffordabilityNote.mListener);
        }

        public int hashCode() {
            return ((0 + java.util.Objects.hash(this.mListener)) * 31) + this.mActionBill.hashCode();
        }
    }

    private final class AgentHandler extends android.os.Handler {
        AgentHandler(android.os.Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x0075 A[Catch: all -> 0x0068, TryCatch #1 {all -> 0x0068, blocks: (B:6:0x0019, B:8:0x0027, B:10:0x002d, B:11:0x003e, B:13:0x0045, B:15:0x004e, B:19:0x006f, B:21:0x0075, B:23:0x0083, B:28:0x0086, B:29:0x008b), top: B:5:0x0019 }] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0083 A[SYNTHETIC] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleMessage(android.os.Message message) {
            int i;
            com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote;
            boolean z;
            switch (message.what) {
                case 0:
                    synchronized (com.android.server.tare.Agent.this.mLock) {
                        removeMessages(0);
                        com.android.server.tare.Agent.this.onAnythingChangedLocked(false);
                    }
                    return;
                case 1:
                    int i2 = message.arg1;
                    java.lang.String str = (java.lang.String) message.obj;
                    synchronized (com.android.server.tare.Agent.this.mLock) {
                        try {
                            android.util.ArraySet arraySet = (android.util.ArraySet) com.android.server.tare.Agent.this.mActionAffordabilityNotes.get(i2, str);
                            if (arraySet != null && arraySet.size() > 0) {
                                long balanceLocked = com.android.server.tare.Agent.this.getBalanceLocked(i2, str);
                                boolean isVip = com.android.server.tare.Agent.this.mIrs.isVip(i2, str);
                                for (int i3 = 0; i3 < arraySet.size(); i3 = i + 1) {
                                    com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote2 = (com.android.server.tare.Agent.ActionAffordabilityNote) arraySet.valueAt(i3);
                                    if (!isVip) {
                                        i = i3;
                                        actionAffordabilityNote = actionAffordabilityNote2;
                                        if (!com.android.server.tare.Agent.this.isAffordableLocked(balanceLocked, actionAffordabilityNote2.getCachedModifiedPrice(), actionAffordabilityNote2.getStockLimitHonoringCtp())) {
                                            z = false;
                                            if (actionAffordabilityNote.isCurrentlyAffordable() == z) {
                                                com.android.server.tare.Agent.ActionAffordabilityNote actionAffordabilityNote3 = actionAffordabilityNote;
                                                actionAffordabilityNote3.setNewAffordability(z);
                                                com.android.server.tare.Agent.this.mIrs.postAffordabilityChanged(i2, str, actionAffordabilityNote3);
                                            }
                                        }
                                    } else {
                                        i = i3;
                                        actionAffordabilityNote = actionAffordabilityNote2;
                                    }
                                    z = true;
                                    if (actionAffordabilityNote.isCurrentlyAffordable() == z) {
                                    }
                                }
                            }
                            com.android.server.tare.Agent.this.scheduleBalanceCheckLocked(i2, str);
                        } finally {
                        }
                    }
                    return;
                default:
                    return;
            }
        }

        void removeAllMessages() {
            removeMessages(0);
            removeMessages(1);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void dumpLocked(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mBalanceThresholdAlarmQueue.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.println("Ongoing events:");
        indentingPrintWriter.increaseIndent();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        int i = 1;
        int numMaps = this.mCurrentOngoingEvents.numMaps() - 1;
        boolean z = false;
        while (numMaps >= 0) {
            int keyAt = this.mCurrentOngoingEvents.keyAt(numMaps);
            int numElementsForKey = this.mCurrentOngoingEvents.numElementsForKey(keyAt) - i;
            while (numElementsForKey >= 0) {
                java.lang.String str = (java.lang.String) this.mCurrentOngoingEvents.keyAt(numMaps, numElementsForKey);
                android.util.SparseArrayMap sparseArrayMap = (android.util.SparseArrayMap) this.mCurrentOngoingEvents.get(keyAt, str);
                int numMaps2 = sparseArrayMap.numMaps() - i;
                int i2 = 0;
                while (numMaps2 >= 0) {
                    int numElementsForKey2 = sparseArrayMap.numElementsForKey(sparseArrayMap.keyAt(numMaps2)) - i;
                    while (numElementsForKey2 >= 0) {
                        if (i2 == 0) {
                            indentingPrintWriter.println(com.android.server.tare.TareUtils.appToString(keyAt, str));
                            indentingPrintWriter.increaseIndent();
                            i2 = i;
                        }
                        com.android.server.tare.Agent.OngoingEvent ongoingEvent = (com.android.server.tare.Agent.OngoingEvent) sparseArrayMap.valueAt(numMaps2, numElementsForKey2);
                        indentingPrintWriter.print(com.android.server.tare.EconomicPolicy.eventToString(ongoingEvent.eventId));
                        if (ongoingEvent.tag != null) {
                            indentingPrintWriter.print("(");
                            indentingPrintWriter.print(ongoingEvent.tag);
                            indentingPrintWriter.print(")");
                        }
                        indentingPrintWriter.print(" runtime=");
                        android.util.TimeUtils.formatDuration(elapsedRealtime - ongoingEvent.startTimeElapsed, indentingPrintWriter);
                        indentingPrintWriter.print(" delta/sec=");
                        indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(ongoingEvent.getDeltaPerSec()));
                        if (ongoingEvent.getCtpPerSec() != 0) {
                            indentingPrintWriter.print(" ctp/sec=");
                            indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(ongoingEvent.getCtpPerSec()));
                        }
                        indentingPrintWriter.print(" refCount=");
                        indentingPrintWriter.print(ongoingEvent.refCount);
                        indentingPrintWriter.println();
                        numElementsForKey2--;
                        i = 1;
                        z = true;
                    }
                    numMaps2--;
                    i = 1;
                }
                if (i2 != 0) {
                    indentingPrintWriter.decreaseIndent();
                }
                numElementsForKey--;
                i = 1;
            }
            numMaps--;
            i = 1;
        }
        if (!z) {
            indentingPrintWriter.print("N/A");
        }
        indentingPrintWriter.decreaseIndent();
    }
}
