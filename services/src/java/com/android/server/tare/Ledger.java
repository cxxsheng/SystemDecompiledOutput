package com.android.server.tare;

/* loaded from: classes2.dex */
class Ledger {
    private static final boolean DEBUG;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_TRANSACTION_COUNT;

    @com.android.internal.annotations.VisibleForTesting
    static final int NUM_REWARD_BUCKET_WINDOWS = 4;
    private static final long REWARD_BUCKET_WINDOW_SIZE_MS = 21600000;
    private static final java.lang.String TAG = "TARE-" + com.android.server.tare.Ledger.class.getSimpleName();
    private static final long TOTAL_REWARD_WINDOW_MS = 86400000;
    private long mCurrentBalance;
    private int mRewardBucketIndex;
    private final com.android.server.tare.Ledger.RewardBucket[] mRewardBuckets;
    private int mTransactionIndex;
    private final com.android.server.tare.Ledger.Transaction[] mTransactions;

    static {
        DEBUG = com.android.server.tare.InternalResourceService.DEBUG || android.util.Log.isLoggable(TAG, 3);
        MAX_TRANSACTION_COUNT = (android.os.Build.IS_ENG || android.os.Build.IS_USERDEBUG || DEBUG) ? 32 : 4;
    }

    static class Transaction {
        public final long ctp;
        public final long delta;
        public final long endTimeMs;
        public final int eventId;
        public final long startTimeMs;

        @android.annotation.Nullable
        public final java.lang.String tag;

        Transaction(long j, long j2, int i, @android.annotation.Nullable java.lang.String str, long j3, long j4) {
            this.startTimeMs = j;
            this.endTimeMs = j2;
            this.eventId = i;
            this.tag = str == null ? null : str.intern();
            this.delta = j3;
            this.ctp = j4;
        }
    }

    static class RewardBucket {
        public final android.util.SparseLongArray cumulativeDelta = new android.util.SparseLongArray();
        public long startTimeMs;

        RewardBucket() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.startTimeMs = 0L;
            this.cumulativeDelta.clear();
        }
    }

    Ledger() {
        this.mCurrentBalance = 0L;
        this.mTransactions = new com.android.server.tare.Ledger.Transaction[MAX_TRANSACTION_COUNT];
        this.mTransactionIndex = 0;
        this.mRewardBuckets = new com.android.server.tare.Ledger.RewardBucket[4];
        this.mRewardBucketIndex = 0;
    }

    Ledger(long j, @android.annotation.NonNull java.util.List<com.android.server.tare.Ledger.Transaction> list, @android.annotation.NonNull java.util.List<com.android.server.tare.Ledger.RewardBucket> list2) {
        this.mCurrentBalance = 0L;
        this.mTransactions = new com.android.server.tare.Ledger.Transaction[MAX_TRANSACTION_COUNT];
        this.mTransactionIndex = 0;
        this.mRewardBuckets = new com.android.server.tare.Ledger.RewardBucket[4];
        this.mRewardBucketIndex = 0;
        this.mCurrentBalance = j;
        int size = list.size();
        for (int max = java.lang.Math.max(0, size - MAX_TRANSACTION_COUNT); max < size; max++) {
            com.android.server.tare.Ledger.Transaction[] transactionArr = this.mTransactions;
            int i = this.mTransactionIndex;
            this.mTransactionIndex = i + 1;
            transactionArr[i] = list.get(max);
        }
        this.mTransactionIndex %= MAX_TRANSACTION_COUNT;
        int size2 = list2.size();
        if (size2 > 0) {
            this.mRewardBucketIndex = -1;
            for (int max2 = java.lang.Math.max(0, size2 - 4); max2 < size2; max2++) {
                com.android.server.tare.Ledger.RewardBucket[] rewardBucketArr = this.mRewardBuckets;
                int i2 = this.mRewardBucketIndex + 1;
                this.mRewardBucketIndex = i2;
                rewardBucketArr[i2] = list2.get(max2);
            }
        }
    }

    long getCurrentBalance() {
        return this.mCurrentBalance;
    }

    @android.annotation.Nullable
    com.android.server.tare.Ledger.Transaction getEarliestTransaction() {
        for (int i = 0; i < this.mTransactions.length; i++) {
            com.android.server.tare.Ledger.Transaction transaction = this.mTransactions[(this.mTransactionIndex + i) % this.mTransactions.length];
            if (transaction != null) {
                return transaction;
            }
        }
        return null;
    }

    @android.annotation.NonNull
    java.util.List<com.android.server.tare.Ledger.RewardBucket> getRewardBuckets() {
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis() - 86400000;
        java.util.ArrayList arrayList = new java.util.ArrayList(4);
        for (int i = 1; i <= 4; i++) {
            com.android.server.tare.Ledger.RewardBucket rewardBucket = this.mRewardBuckets[(this.mRewardBucketIndex + i) % 4];
            if (rewardBucket != null) {
                if (currentTimeMillis <= rewardBucket.startTimeMs) {
                    arrayList.add(rewardBucket);
                } else {
                    rewardBucket.reset();
                }
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    java.util.List<com.android.server.tare.Ledger.Transaction> getTransactions() {
        java.util.ArrayList arrayList = new java.util.ArrayList(MAX_TRANSACTION_COUNT);
        for (int i = 0; i < MAX_TRANSACTION_COUNT; i++) {
            com.android.server.tare.Ledger.Transaction transaction = this.mTransactions[(this.mTransactionIndex + i) % MAX_TRANSACTION_COUNT];
            if (transaction != null) {
                arrayList.add(transaction);
            }
        }
        return arrayList;
    }

    void recordTransaction(@android.annotation.NonNull com.android.server.tare.Ledger.Transaction transaction) {
        this.mTransactions[this.mTransactionIndex] = transaction;
        this.mCurrentBalance += transaction.delta;
        this.mTransactionIndex = (this.mTransactionIndex + 1) % MAX_TRANSACTION_COUNT;
        if (com.android.server.tare.EconomicPolicy.isReward(transaction.eventId)) {
            com.android.server.tare.Ledger.RewardBucket currentRewardBucket = getCurrentRewardBucket();
            currentRewardBucket.cumulativeDelta.put(transaction.eventId, currentRewardBucket.cumulativeDelta.get(transaction.eventId, 0L) + transaction.delta);
        }
    }

    @android.annotation.NonNull
    private com.android.server.tare.Ledger.RewardBucket getCurrentRewardBucket() {
        com.android.server.tare.Ledger.RewardBucket rewardBucket = this.mRewardBuckets[this.mRewardBucketIndex];
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        if (rewardBucket == null) {
            com.android.server.tare.Ledger.RewardBucket rewardBucket2 = new com.android.server.tare.Ledger.RewardBucket();
            rewardBucket2.startTimeMs = currentTimeMillis;
            this.mRewardBuckets[this.mRewardBucketIndex] = rewardBucket2;
            return rewardBucket2;
        }
        if (currentTimeMillis - rewardBucket.startTimeMs < REWARD_BUCKET_WINDOW_SIZE_MS) {
            return rewardBucket;
        }
        this.mRewardBucketIndex = (this.mRewardBucketIndex + 1) % 4;
        com.android.server.tare.Ledger.RewardBucket rewardBucket3 = this.mRewardBuckets[this.mRewardBucketIndex];
        if (rewardBucket3 == null) {
            rewardBucket3 = new com.android.server.tare.Ledger.RewardBucket();
            this.mRewardBuckets[this.mRewardBucketIndex] = rewardBucket3;
        }
        rewardBucket3.reset();
        rewardBucket3.startTimeMs = currentTimeMillis;
        return rewardBucket3;
    }

    long get24HourSum(int i, long j) {
        long j2 = j - 86400000;
        long j3 = 0;
        for (int i2 = 0; i2 < this.mRewardBuckets.length; i2++) {
            com.android.server.tare.Ledger.RewardBucket rewardBucket = this.mRewardBuckets[i2];
            if (rewardBucket != null && rewardBucket.startTimeMs >= j2 && rewardBucket.startTimeMs < j) {
                j3 += rewardBucket.cumulativeDelta.get(i, 0L);
            }
        }
        return j3;
    }

    @android.annotation.Nullable
    com.android.server.tare.Ledger.Transaction removeOldTransactions(long j) {
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis() - j;
        for (int i = 0; i < this.mTransactions.length; i++) {
            int length = (this.mTransactionIndex + i) % this.mTransactions.length;
            com.android.server.tare.Ledger.Transaction transaction = this.mTransactions[length];
            if (transaction != null) {
                if (transaction.endTimeMs <= currentTimeMillis) {
                    this.mTransactions[length] = null;
                } else {
                    return transaction;
                }
            }
        }
        return null;
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter, int i) {
        indentingPrintWriter.print("Current balance", com.android.server.tare.TareUtils.cakeToString(getCurrentBalance())).println();
        indentingPrintWriter.println();
        boolean z = false;
        for (int i2 = 0; i2 < java.lang.Math.min(MAX_TRANSACTION_COUNT, i); i2++) {
            com.android.server.tare.Ledger.Transaction transaction = this.mTransactions[(this.mTransactionIndex + i2) % MAX_TRANSACTION_COUNT];
            if (transaction != null) {
                if (!z) {
                    indentingPrintWriter.println("Transactions:");
                    indentingPrintWriter.increaseIndent();
                    z = true;
                }
                android.util.TimeUtils.dumpTime(indentingPrintWriter, transaction.startTimeMs);
                indentingPrintWriter.print("--");
                android.util.TimeUtils.dumpTime(indentingPrintWriter, transaction.endTimeMs);
                indentingPrintWriter.print(": ");
                indentingPrintWriter.print(com.android.server.tare.EconomicPolicy.eventToString(transaction.eventId));
                if (transaction.tag != null) {
                    indentingPrintWriter.print("(");
                    indentingPrintWriter.print(transaction.tag);
                    indentingPrintWriter.print(")");
                }
                indentingPrintWriter.print(" --> ");
                indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(transaction.delta));
                indentingPrintWriter.print(" (ctp=");
                indentingPrintWriter.print(com.android.server.tare.TareUtils.cakeToString(transaction.ctp));
                indentingPrintWriter.println(")");
            }
        }
        if (z) {
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
        long currentTimeMillis = com.android.server.tare.TareUtils.getCurrentTimeMillis();
        boolean z2 = false;
        for (int i3 = 0; i3 < 4; i3++) {
            com.android.server.tare.Ledger.RewardBucket rewardBucket = this.mRewardBuckets[((this.mRewardBucketIndex - i3) + 4) % 4];
            if (rewardBucket != null && rewardBucket.startTimeMs != 0) {
                if (!z2) {
                    indentingPrintWriter.println("Reward buckets:");
                    indentingPrintWriter.increaseIndent();
                    z2 = true;
                }
                android.util.TimeUtils.dumpTime(indentingPrintWriter, rewardBucket.startTimeMs);
                indentingPrintWriter.print(" (");
                android.util.TimeUtils.formatDuration(currentTimeMillis - rewardBucket.startTimeMs, indentingPrintWriter);
                indentingPrintWriter.println(" ago):");
                indentingPrintWriter.increaseIndent();
                for (int i4 = 0; i4 < rewardBucket.cumulativeDelta.size(); i4++) {
                    indentingPrintWriter.print(com.android.server.tare.EconomicPolicy.eventToString(rewardBucket.cumulativeDelta.keyAt(i4)));
                    indentingPrintWriter.print(": ");
                    indentingPrintWriter.println(com.android.server.tare.TareUtils.cakeToString(rewardBucket.cumulativeDelta.valueAt(i4)));
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
        if (z2) {
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
    }
}
