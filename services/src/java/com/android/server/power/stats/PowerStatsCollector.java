package com.android.server.power.stats;

/* loaded from: classes2.dex */
public abstract class PowerStatsCollector {
    private static final int MILLIVOLTS_PER_VOLT = 1000;
    private static final java.lang.String TAG = "PowerStatsCollector";
    protected final com.android.internal.os.Clock mClock;
    private boolean mEnabled;
    private final android.os.Handler mHandler;
    private final long mThrottlePeriodMs;
    private final java.lang.Runnable mCollectAndDeliverStats = new java.lang.Runnable() { // from class: com.android.server.power.stats.PowerStatsCollector$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.power.stats.PowerStatsCollector.this.collectAndDeliverStats();
        }
    };
    private long mLastScheduledUpdateMs = -1;

    @com.android.internal.annotations.GuardedBy({"this"})
    private volatile java.util.List<java.util.function.Consumer<com.android.internal.os.PowerStats>> mConsumerList = java.util.Collections.emptyList();

    @android.annotation.Nullable
    protected abstract com.android.internal.os.PowerStats collectStats();

    public static class StatsArrayLayout {
        private static final java.lang.String EXTRA_DEVICE_DURATION_POSITION = "dd";
        private static final java.lang.String EXTRA_DEVICE_ENERGY_CONSUMERS_COUNT = "dec";
        private static final java.lang.String EXTRA_DEVICE_ENERGY_CONSUMERS_POSITION = "de";
        private static final java.lang.String EXTRA_DEVICE_POWER_POSITION = "dp";
        private static final java.lang.String EXTRA_UID_POWER_POSITION = "up";
        protected static final double MILLI_TO_NANO_MULTIPLIER = 1000000.0d;
        protected int mDeviceDurationPosition;
        private int mDeviceEnergyConsumerCount;
        private int mDeviceEnergyConsumerPosition;
        private int mDevicePowerEstimatePosition;
        private int mDeviceStatsArrayLength;
        private int mUidPowerEstimatePosition;
        private int mUidStatsArrayLength;

        public int getDeviceStatsArrayLength() {
            return this.mDeviceStatsArrayLength;
        }

        public int getUidStatsArrayLength() {
            return this.mUidStatsArrayLength;
        }

        protected int addDeviceSection(int i) {
            int i2 = this.mDeviceStatsArrayLength;
            this.mDeviceStatsArrayLength += i;
            return i2;
        }

        protected int addUidSection(int i) {
            int i2 = this.mUidStatsArrayLength;
            this.mUidStatsArrayLength += i;
            return i2;
        }

        public void addDeviceSectionUsageDuration() {
            this.mDeviceDurationPosition = addDeviceSection(1);
        }

        public void setUsageDuration(long[] jArr, long j) {
            jArr[this.mDeviceDurationPosition] = j;
        }

        public long getUsageDuration(long[] jArr) {
            return jArr[this.mDeviceDurationPosition];
        }

        public void addDeviceSectionEnergyConsumers(int i) {
            this.mDeviceEnergyConsumerPosition = addDeviceSection(i);
            this.mDeviceEnergyConsumerCount = i;
        }

        public int getEnergyConsumerCount() {
            return this.mDeviceEnergyConsumerCount;
        }

        public void setConsumedEnergy(long[] jArr, int i, long j) {
            jArr[this.mDeviceEnergyConsumerPosition + i] = j;
        }

        public long getConsumedEnergy(long[] jArr, int i) {
            return jArr[this.mDeviceEnergyConsumerPosition + i];
        }

        public void addDeviceSectionPowerEstimate() {
            this.mDevicePowerEstimatePosition = addDeviceSection(1);
        }

        public void setDevicePowerEstimate(long[] jArr, double d) {
            jArr[this.mDevicePowerEstimatePosition] = (long) (d * MILLI_TO_NANO_MULTIPLIER);
        }

        public double getDevicePowerEstimate(long[] jArr) {
            return jArr[this.mDevicePowerEstimatePosition] / MILLI_TO_NANO_MULTIPLIER;
        }

        public void addUidSectionPowerEstimate() {
            this.mUidPowerEstimatePosition = addUidSection(1);
        }

        public void setUidPowerEstimate(long[] jArr, double d) {
            jArr[this.mUidPowerEstimatePosition] = (long) (d * MILLI_TO_NANO_MULTIPLIER);
        }

        public double getUidPowerEstimate(long[] jArr) {
            return jArr[this.mUidPowerEstimatePosition] / MILLI_TO_NANO_MULTIPLIER;
        }

        public void toExtras(android.os.PersistableBundle persistableBundle) {
            persistableBundle.putInt(EXTRA_DEVICE_DURATION_POSITION, this.mDeviceDurationPosition);
            persistableBundle.putInt(EXTRA_DEVICE_ENERGY_CONSUMERS_POSITION, this.mDeviceEnergyConsumerPosition);
            persistableBundle.putInt(EXTRA_DEVICE_ENERGY_CONSUMERS_COUNT, this.mDeviceEnergyConsumerCount);
            persistableBundle.putInt(EXTRA_DEVICE_POWER_POSITION, this.mDevicePowerEstimatePosition);
            persistableBundle.putInt("up", this.mUidPowerEstimatePosition);
        }

        public void fromExtras(android.os.PersistableBundle persistableBundle) {
            this.mDeviceDurationPosition = persistableBundle.getInt(EXTRA_DEVICE_DURATION_POSITION);
            this.mDeviceEnergyConsumerPosition = persistableBundle.getInt(EXTRA_DEVICE_ENERGY_CONSUMERS_POSITION);
            this.mDeviceEnergyConsumerCount = persistableBundle.getInt(EXTRA_DEVICE_ENERGY_CONSUMERS_COUNT);
            this.mDevicePowerEstimatePosition = persistableBundle.getInt(EXTRA_DEVICE_POWER_POSITION);
            this.mUidPowerEstimatePosition = persistableBundle.getInt("up");
        }

        protected void putIntArray(android.os.PersistableBundle persistableBundle, java.lang.String str, int[] iArr) {
            if (iArr == null) {
                return;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (int i : iArr) {
                if (!sb.isEmpty()) {
                    sb.append(',');
                }
                sb.append(i);
            }
            persistableBundle.putString(str, sb.toString());
        }

        protected int[] getIntArray(android.os.PersistableBundle persistableBundle, java.lang.String str) {
            java.lang.String string = persistableBundle.getString(str);
            if (string == null) {
                return null;
            }
            java.lang.String[] split = string.trim().split(",");
            int[] iArr = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                try {
                    iArr[i] = java.lang.Integer.parseInt(split[i]);
                } catch (java.lang.NumberFormatException e) {
                    android.util.Slog.wtf(com.android.server.power.stats.PowerStatsCollector.TAG, "Invalid CSV format: " + string);
                    return null;
                }
            }
            return iArr;
        }
    }

    public PowerStatsCollector(android.os.Handler handler, long j, com.android.internal.os.Clock clock) {
        this.mHandler = handler;
        this.mThrottlePeriodMs = j;
        this.mClock = clock;
    }

    public void addConsumer(java.util.function.Consumer<com.android.internal.os.PowerStats> consumer) {
        synchronized (this) {
            try {
                if (this.mConsumerList.contains(consumer)) {
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mConsumerList);
                arrayList.add(consumer);
                this.mConsumerList = java.util.Collections.unmodifiableList(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeConsumer(java.util.function.Consumer<com.android.internal.os.PowerStats> consumer) {
        synchronized (this) {
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mConsumerList);
            arrayList.remove(consumer);
            this.mConsumerList = java.util.Collections.unmodifiableList(arrayList);
        }
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void collectAndDeliverStats() {
        com.android.internal.os.PowerStats collectStats = collectStats();
        if (collectStats == null) {
            return;
        }
        java.util.List<java.util.function.Consumer<com.android.internal.os.PowerStats>> list = this.mConsumerList;
        for (int size = list.size() - 1; size >= 0; size--) {
            list.get(size).accept(collectStats);
        }
    }

    public boolean schedule() {
        if (!this.mEnabled) {
            return false;
        }
        long uptimeMillis = this.mClock.uptimeMillis();
        if (uptimeMillis - this.mLastScheduledUpdateMs < this.mThrottlePeriodMs && this.mLastScheduledUpdateMs >= 0) {
            return false;
        }
        this.mLastScheduledUpdateMs = uptimeMillis;
        this.mHandler.post(this.mCollectAndDeliverStats);
        return true;
    }

    public boolean forceSchedule() {
        if (!this.mEnabled) {
            return false;
        }
        this.mHandler.removeCallbacks(this.mCollectAndDeliverStats);
        this.mHandler.postAtFrontOfQueue(this.mCollectAndDeliverStats);
        return true;
    }

    public void collectAndDump(java.io.PrintWriter printWriter) {
        if (java.lang.Thread.currentThread() == this.mHandler.getLooper().getThread()) {
            throw new java.lang.RuntimeException("Calling this method from the handler thread would cause a deadlock");
        }
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        indentingPrintWriter.print(getClass().getSimpleName());
        if (!isEnabled()) {
            indentingPrintWriter.println(": disabled");
            return;
        }
        indentingPrintWriter.println();
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Objects.requireNonNull(arrayList);
        java.util.function.Consumer<com.android.internal.os.PowerStats> consumer = new java.util.function.Consumer() { // from class: com.android.server.power.stats.PowerStatsCollector$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add((com.android.internal.os.PowerStats) obj);
            }
        };
        addConsumer(consumer);
        try {
            if (forceSchedule()) {
                awaitCompletion();
            }
            removeConsumer(consumer);
            indentingPrintWriter.increaseIndent();
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((com.android.internal.os.PowerStats) it.next()).dump(indentingPrintWriter);
            }
            indentingPrintWriter.decreaseIndent();
        } catch (java.lang.Throwable th) {
            removeConsumer(consumer);
            throw th;
        }
    }

    private void awaitCompletion() {
        android.os.ConditionVariable conditionVariable = new android.os.ConditionVariable();
        android.os.Handler handler = this.mHandler;
        java.util.Objects.requireNonNull(conditionVariable);
        handler.post(new com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda11(conditionVariable));
        conditionVariable.block();
    }

    protected long uJtoUc(long j, int i) {
        return ((j * 1000) + (i / 2)) / i;
    }
}
