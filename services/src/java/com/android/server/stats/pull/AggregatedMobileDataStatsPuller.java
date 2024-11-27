package com.android.server.stats.pull;

/* loaded from: classes2.dex */
class AggregatedMobileDataStatsPuller {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "AggregatedMobileDataStatsPuller";
    private final android.os.Handler mMobileDataStatsHandler;
    private final android.app.usage.NetworkStatsManager mNetworkStatsManager;
    private final java.lang.Object mLock = new java.lang.Object();
    private android.net.NetworkStats mLastMobileUidStats = new android.net.NetworkStats(0, -1);
    private final com.android.server.selinux.RateLimiter mRateLimiter = new com.android.server.selinux.RateLimiter(java.time.Duration.ofSeconds(1));

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<com.android.server.stats.pull.AggregatedMobileDataStatsPuller.UidProcState, com.android.server.stats.pull.AggregatedMobileDataStatsPuller.MobileDataStats> mUidStats = new android.util.ArrayMap();
    private final android.util.SparseIntArray mUidPreviousState = new android.util.SparseIntArray();

    private static class UidProcState {
        private final int mState;
        private final int mUid;

        UidProcState(int i, int i2) {
            this.mUid = i;
            this.mState = i2;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.stats.pull.AggregatedMobileDataStatsPuller.UidProcState)) {
                return false;
            }
            com.android.server.stats.pull.AggregatedMobileDataStatsPuller.UidProcState uidProcState = (com.android.server.stats.pull.AggregatedMobileDataStatsPuller.UidProcState) obj;
            return this.mUid == uidProcState.mUid && this.mState == uidProcState.mState;
        }

        public int hashCode() {
            return (this.mUid * 31) + this.mState;
        }

        public int getUid() {
            return this.mUid;
        }

        public int getState() {
            return this.mState;
        }
    }

    private static class MobileDataStats {
        private long mRxBytes;
        private long mRxPackets;
        private long mTxBytes;
        private long mTxPackets;

        private MobileDataStats() {
            this.mRxPackets = 0L;
            this.mTxPackets = 0L;
            this.mRxBytes = 0L;
            this.mTxBytes = 0L;
        }

        public long getRxPackets() {
            return this.mRxPackets;
        }

        public long getTxPackets() {
            return this.mTxPackets;
        }

        public long getRxBytes() {
            return this.mRxBytes;
        }

        public long getTxBytes() {
            return this.mTxBytes;
        }

        public void addRxPackets(long j) {
            this.mRxPackets += j;
        }

        public void addTxPackets(long j) {
            this.mTxPackets += j;
        }

        public void addRxBytes(long j) {
            this.mRxBytes += j;
        }

        public void addTxBytes(long j) {
            this.mTxBytes += j;
        }

        public boolean isEmpty() {
            return this.mRxPackets == 0 && this.mTxPackets == 0 && this.mRxBytes == 0 && this.mTxBytes == 0;
        }
    }

    AggregatedMobileDataStatsPuller(android.app.usage.NetworkStatsManager networkStatsManager) {
        this.mNetworkStatsManager = networkStatsManager;
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("MobileDataStatsHandler");
        handlerThread.start();
        this.mMobileDataStatsHandler = new android.os.Handler(handlerThread.getLooper());
        if (this.mNetworkStatsManager != null) {
            this.mMobileDataStatsHandler.post(new java.lang.Runnable() { // from class: com.android.server.stats.pull.AggregatedMobileDataStatsPuller$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.stats.pull.AggregatedMobileDataStatsPuller.this.lambda$new$0();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        updateNetworkStats(this.mNetworkStatsManager);
    }

    public void noteUidProcessState(final int i, final int i2, long j, long j2) {
        this.mMobileDataStatsHandler.post(new java.lang.Runnable() { // from class: com.android.server.stats.pull.AggregatedMobileDataStatsPuller$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.stats.pull.AggregatedMobileDataStatsPuller.this.lambda$noteUidProcessState$1(i, i2);
            }
        });
    }

    public int pullDataBytesTransfer(java.util.List<android.util.StatsEvent> list) {
        int pullDataBytesTransferLocked;
        synchronized (this.mLock) {
            pullDataBytesTransferLocked = pullDataBytesTransferLocked(list);
        }
        return pullDataBytesTransferLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.stats.pull.AggregatedMobileDataStatsPuller.MobileDataStats getUidStatsForPreviousStateLocked(int i) {
        com.android.server.stats.pull.AggregatedMobileDataStatsPuller.UidProcState uidProcState = new com.android.server.stats.pull.AggregatedMobileDataStatsPuller.UidProcState(i, this.mUidPreviousState.get(i, -1));
        if (this.mUidStats.containsKey(uidProcState)) {
            return this.mUidStats.get(uidProcState);
        }
        com.android.server.stats.pull.AggregatedMobileDataStatsPuller.MobileDataStats mobileDataStats = new com.android.server.stats.pull.AggregatedMobileDataStatsPuller.MobileDataStats();
        this.mUidStats.put(uidProcState, mobileDataStats);
        return mobileDataStats;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: noteUidProcessStateImpl, reason: merged with bridge method [inline-methods] */
    public void lambda$noteUidProcessState$1(int i, int i2) {
        if (this.mRateLimiter.tryAcquire()) {
            if (this.mNetworkStatsManager != null) {
                updateNetworkStats(this.mNetworkStatsManager);
            } else {
                android.util.Slog.w(TAG, "noteUidProcessStateLocked() can not get mNetworkStatsManager");
            }
        }
        this.mUidPreviousState.put(i, i2);
    }

    private void updateNetworkStats(android.app.usage.NetworkStatsManager networkStatsManager) {
        android.net.NetworkStats mobileUidStats = networkStatsManager.getMobileUidStats();
        if (isEmpty(mobileUidStats)) {
            return;
        }
        android.net.NetworkStats subtract = mobileUidStats.subtract(this.mLastMobileUidStats);
        this.mLastMobileUidStats = mobileUidStats;
        if (!isEmpty(subtract)) {
            updateNetworkStatsDelta(subtract);
        }
    }

    private void updateNetworkStatsDelta(android.net.NetworkStats networkStats) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator it = networkStats.iterator();
                while (it.hasNext()) {
                    android.net.NetworkStats.Entry entry = (android.net.NetworkStats.Entry) it.next();
                    if (entry.getRxPackets() != 0 || entry.getTxPackets() != 0) {
                        com.android.server.stats.pull.AggregatedMobileDataStatsPuller.MobileDataStats uidStatsForPreviousStateLocked = getUidStatsForPreviousStateLocked(entry.getUid());
                        uidStatsForPreviousStateLocked.addTxBytes(entry.getTxBytes());
                        uidStatsForPreviousStateLocked.addRxBytes(entry.getRxBytes());
                        uidStatsForPreviousStateLocked.addTxPackets(entry.getTxPackets());
                        uidStatsForPreviousStateLocked.addRxPackets(entry.getRxPackets());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int pullDataBytesTransferLocked(java.util.List<android.util.StatsEvent> list) {
        for (java.util.Map.Entry<com.android.server.stats.pull.AggregatedMobileDataStatsPuller.UidProcState, com.android.server.stats.pull.AggregatedMobileDataStatsPuller.MobileDataStats> entry : this.mUidStats.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                com.android.server.stats.pull.AggregatedMobileDataStatsPuller.MobileDataStats value = entry.getValue();
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_PROC_STATE, entry.getKey().getUid(), android.app.ActivityManager.processStateAmToProto(entry.getKey().getState()), value.getRxBytes(), value.getRxPackets(), value.getTxBytes(), value.getTxPackets()));
            }
        }
        return 0;
    }

    private static boolean isEmpty(android.net.NetworkStats networkStats) {
        long j;
        long j2;
        java.util.Iterator it = networkStats.iterator();
        while (it.hasNext()) {
            android.net.NetworkStats.Entry entry = (android.net.NetworkStats.Entry) it.next();
            if (entry.getRxPackets() != 0 || entry.getTxPackets() != 0) {
                j = entry.getRxPackets() + 0;
                j2 = entry.getTxPackets() + 0;
                break;
            }
        }
        j = 0;
        j2 = 0;
        return j + j2 == 0;
    }
}
